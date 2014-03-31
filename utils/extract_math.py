#!/usr/bin/env python

import os
import sys
import string
import fnmatch
import json
#import xml.etree.ElementTree as ET
import lxml.etree as ET

if len(sys.argv) < 3:
	print("usage: %s [INPUT_DIR] [OUTPUT_DIR]" % sys.argv[0])
	sys.exit(1)

source_dir = sys.argv[1]
output_dir = sys.argv[2]
max_for_element = 100
pretty_print = True

elements = ["mrow", "mfrac", "msqrt", "mroot", "mstyle", "merror", "mpadded",
            "mphantom", "mfenced", "menclose", "msub", "msup", "msubsup",
	    "munder", "mover", "munderover", "mmultiscripts", "mtable",
	    "mlabeledtr", "mtr", "mtd", "mstack", "mlongdiv", "msgroup",
	    "msrow", "mscarries", "mscarry", "maction" ]

namespaces = {"math": "http://www.w3.org/1998/Math/MathML"}

element_map = dict()

for element in elements:
	element_map[element] = []

def is_better(current, formulas):
	for i, (doc, n, occ) in enumerate(formulas):
		if current > occ:
			del formulas[i]
			return True
	return False

def math_by_elements(filename):
	json_file = open(filename, mode="r")
	json_data = json.load(json_file)

	formulas = json_data.get("SimilarityDoc").get("mathMathML")

	for i, formula in enumerate(formulas):
		for element in elements:
			occur = formula.count("<" + element)
			if occur > 0 and (len(element_map[element]) < max_for_element  or is_better(occur, element_map[element])):
				element_map[element].append((filename, i, occur))
	json_file.close()

def write_file(json_filename, output_dir):
	json_file = open(json_filename, mode="r")
	json_data = json.load(json_file)

	formulas = json_data.get("SimilarityDoc").get("mathMathML")

	for element, formulas_list in element_map.iteritems():
		for doc, formula, occ in formulas_list:
			if doc == json_filename:
				filename = os.path.join(output_dir, element, "%s_%d.xml" % (string.replace(doc, "/", "_"), formula))
				file = open(filename, mode="w")
        			file.write("<?xml version=\"1.0\"?>\n".encode("UTF-8"))
	                        
				if pretty_print:
					root = ET.fromstring(formulas[formula].encode("UTF-8"))
					file.write(ET.tostring(root, encoding="UTF-8", pretty_print=True))
				else:
        				file.write(formulas[formula].encode("UTF-8"))
	                        
				file.close()
	json_file.close()

	

def extract(directory):
	i = 0
	for (root, dirs, files) in os.walk(directory):
		for name in files:
			if fnmatch.fnmatch(name, "*.json"):
				path = os.path.join(root, name)
				print("%d: %s" % (i, path))
				math_by_elements(path)
				i += 1

def write_files(directory):
	i = 0
	for root, dirs, files in os.walk(directory):
		for name in files:
			if fnmatch.fnmatch(name, "*.json"):
				path = os.path.join(root, name)
				print("%d: %s" % (i, path))
				write_file(path, output_dir)
				i += 1

try:
	os.mkdir(output_dir)
	for element in elements:
		os.mkdir(os.path.join(output_dir, element))
except:
	print("existing output directory")


extract(source_dir)
print("\nwriting files")
write_files(source_dir)


