/*
 * Copyright 2014 Faculty of Informatics Masaryk University.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.datatables;

import java.util.List;

/**
 * taken from meditor
 * @author Dominik Szalai - emptulik at gmail.com
 * @param <T>
 */
public class DataTablesResponse<T>
{

    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private List<T> data;
    private String error;

    public DataTablesResponse()
    {

    }

    public DataTablesResponse(DataTablesRequest dataTablesRequest, List<T> data, int recordsTotal, int recordsFiltered)
    {
        this.draw = dataTablesRequest.getDraw();
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
        //this.recordsFiltered = this.data.size();
    }

    public int getDraw()
    {
        return draw;
    }

    public void setDraw(int draw)
    {
        this.draw = draw;
    }

    public int getRecordsTotal()
    {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal)
    {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered()
    {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered)
    {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData()
    {
        return data;
    }

    public void setData(List<T> data)
    {
        this.data = data;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    @Override
    public String toString()
    {
        return "DataTablesResponse{" + "draw=" + draw + ", recordsTotal=" + recordsTotal + ", recordsFiltered=" + recordsFiltered + ", data=" + data + ", error=" + error + '}';
    }
}