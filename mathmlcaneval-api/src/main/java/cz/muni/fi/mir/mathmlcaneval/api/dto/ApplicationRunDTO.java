/*
 * Copyright 2016 MIR@MU.
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
package cz.muni.fi.mir.mathmlcaneval.api.dto;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Data
public class ApplicationRunDTO implements Serializable
{
    private static final long serialVersionUID = -3367780482829168589L;
    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private String note;
    private GitRevisionDTO gitRevision;
    private ConfigurationDTO configuration;
    private UserDTO user;
}
