/*
 * Copyright 2022 Marco Bignami.
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
package net.unknowndomain.alea.systems.fitd;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.unknowndomain.alea.systems.RpgSystemOptions;
import net.unknowndomain.alea.systems.annotations.RpgSystemData;
import net.unknowndomain.alea.systems.annotations.RpgSystemOption;


/**
 *
 * @author journeyman
 */
@RpgSystemData(bundleName = "net.unknowndomain.alea.systems.fitd.RpgSystemBundle")
public class ForgedDarkOptions extends RpgSystemOptions
{
    @RpgSystemOption(name = "dice", shortcode = "d", description = "fitd.options.dice", argName = "numberOfDice")
    private Integer diceNumber;
            
                        
    @Override
    public boolean isValid()
    {
        return !(
                isHelp() || (diceNumber == null)
                );
    }

    public Integer getDiceNumber()
    {
        return diceNumber;
    }

    public Collection<ForgedDarkModifiers> getModifiers()
    {
        Set<ForgedDarkModifiers> mods = new HashSet<>();
        if (isVerbose())
        {
            mods.add(ForgedDarkModifiers.VERBOSE);
        }
        return mods;
    }
    
}