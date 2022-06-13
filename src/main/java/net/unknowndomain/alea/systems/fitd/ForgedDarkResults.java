/*
 * Copyright 2020 Marco Bignami.
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.roll.LocalizedResult;

/**
 *
 * @author journeyman
 */
public class ForgedDarkResults extends LocalizedResult
{
    private final static String BUNDLE_NAME = "net.unknowndomain.alea.systems.fitd.RpgSystemBundle";
    
    private final List<SingleResult<Integer>> results;
    private int successLevel = 0;
    
    public ForgedDarkResults(List<SingleResult<Integer>> results)
    {
        List<SingleResult<Integer>> tmp = new ArrayList<>(results.size());
        tmp.addAll(results);
        this.results = Collections.unmodifiableList(tmp);
    }

    public List<SingleResult<Integer>> getResults()
    {
        return results;
    }

    @Override
    protected void formatResults(MsgBuilder messageBuilder, boolean verbose, int indentValue)
    {
        messageBuilder.append(translate("fitd.results.successLevel."+successLevel)).appendNewLine();
        if (verbose)
        {
            messageBuilder.append("Roll ID: ").append(getUuid()).appendNewLine();
            messageBuilder.append(translate("fitd.results.diceResults")).append(" [ ");
            for (SingleResult<Integer> t : getResults())
            {
                messageBuilder.append("( ").append(t.getLabel()).append(" => ");
                messageBuilder.append(t.getValue()).append(") ");
            }
            messageBuilder.append("]").appendNewLine();
        }
    }

    @Override
    protected String getBundleName()
    {
        return BUNDLE_NAME;
    }

    public int getSuccessLevel()
    {
        return successLevel;
    }

    public void setSuccessLevel(int successLevel)
    {
        this.successLevel = successLevel;
    }
    
}
