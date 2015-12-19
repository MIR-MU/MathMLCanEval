<%-- 
    Document   : sidebar
    Created on : Dec 19, 2015, 10:39:14 AM
    Author     : Dominik Szalai - emptulik at gmail.com
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="card">
    <div class="card-block">
        <form>
            <fieldset class="form-group">
                <label for="formGroupExampleInput">Username</label>
                <input type="text" class="form-control" id="formGroupExampleInput" placeholder="Example input">
            </fieldset>
            <fieldset class="form-group">
                <label for="formGroupExampleInput2">Password</label>
                <input type="text" class="form-control" id="formGroupExampleInput2" placeholder="Another input">
            </fieldset>
            <button class="btn btn-primary-outline pull-xs-right">log in</button>
        </form>
    </div>
    <div class="card-block">
        <h4 class="card-title">Explore</h4>
        <p class="card-text">Browse uploaded formulas or search for specific formula.</p>
        <a href="#" class="btn btn-primary">Explore formulas</a>
    </div>
    <div class="card-block">
        <h4 class="card-title">Statistics</h4>
        <p class="card-text">See the evaluation and statistics about performance and correctness of canonicalizer.</p>
        <a href="#" class="btn btn-primary">Checkout statistics</a>
    </div>
</div>