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
package cz.muni.fi.mir.mathmlcaneval.webapp.forms;

import lombok.Data;
import org.apache.commons.collections4.FactoryUtils;
import org.apache.commons.collections4.list.LazyList;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Data
public class UserForm implements Form
{
    private Long id;
    @Email
    private String email;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordRepeat;
    @NotEmpty
    private String realName;
    @Size(min = 1)
    private List<UserRoleForm> roles = LazyList.lazyList(new ArrayList<UserRoleForm>(), FactoryUtils.instantiateFactory(UserRoleForm.class));
}
