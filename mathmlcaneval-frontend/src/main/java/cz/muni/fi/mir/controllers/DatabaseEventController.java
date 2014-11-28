/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.controllers;

import cz.muni.fi.mir.datatables.DataTablesDatabaseEvent;
import cz.muni.fi.mir.datatables.DataTablesRequest;
import cz.muni.fi.mir.datatables.DataTablesResponse;
import cz.muni.fi.mir.db.interceptors.DatabaseEvent;
import cz.muni.fi.mir.db.interceptors.DatabaseEventService;
import cz.muni.fi.mir.db.domain.SearchResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Controller
@RequestMapping(value = "/databaseevent")
public class DatabaseEventController
{
    @Autowired
    private DatabaseEventService databaseEventService;
    
    
    @RequestMapping(value = "/list",produces = "application/json; charset=utf-8")
    public @ResponseBody DataTablesResponse<DataTablesDatabaseEvent> getAjaxData(@ModelAttribute DataTablesRequest dataTablesRequest)
    {
        SearchResponse<DatabaseEvent> response = databaseEventService.getLatestEvents(null, null, 
                dataTablesRequest.getSearchValue(),
                dataTablesRequest.getStart(), 
                dataTablesRequest.getStart()+dataTablesRequest.getLength());
        
        
        List<DataTablesDatabaseEvent> list = new ArrayList<>();
        
        for(DatabaseEvent de : response.getResults())
        {
            list.add(new DataTablesDatabaseEvent(de));
        }
        
        DataTablesResponse<DataTablesDatabaseEvent> result = new DataTablesResponse<>(dataTablesRequest, 
                list, 
                databaseEventService.getNumberOfEvents().intValue(), 
                response.getViewSize()
        );
        
        return result;
    }
            
}
