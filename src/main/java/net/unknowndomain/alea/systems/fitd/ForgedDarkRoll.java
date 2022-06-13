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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.random.SingleResultComparator;
import net.unknowndomain.alea.random.dice.DicePool;
import net.unknowndomain.alea.random.dice.bag.D6;
import net.unknowndomain.alea.roll.GenericResult;
import net.unknowndomain.alea.roll.GenericRoll;

/**
 *
 * @author journeyman
 */
public class ForgedDarkRoll implements GenericRoll
{
    
    
    private final DicePool<D6> dicePool;
    private final Integer numberOfDice;
    private final Set<ForgedDarkModifiers> mods;
    private final Locale lang;
    
    public ForgedDarkRoll(Integer numberOfDice, Locale lang, ForgedDarkModifiers ... mod)
    {
        this(numberOfDice, lang, Arrays.asList(mod));
    }
    
    public ForgedDarkRoll(Integer numberOfDice, Locale lang, Collection<ForgedDarkModifiers> mod)
    {
        this.mods = new HashSet<>();
        if (mod != null)
        {
            this.mods.addAll(mod);
        }
        int dice = numberOfDice;
        if (numberOfDice <= 0)
        {
            dice = 2;
            mods.add(ForgedDarkModifiers.DESPERATE);
        }
        if (numberOfDice > 6 )
        {
            numberOfDice = 6;
        }
        this.dicePool = new DicePool<>(D6.INSTANCE, dice);
        this.numberOfDice = numberOfDice;
        this.lang = lang;
    }
    
    @Override
    public GenericResult getResult()
    {
        List<SingleResult<Integer>> resultsPool = this.dicePool.getResults();
        List<SingleResult<Integer>> res = new ArrayList<>(resultsPool.size());
        res.addAll(resultsPool);
        ForgedDarkResults results = buildResults(res);
        results.setVerbose(mods.contains(ForgedDarkModifiers.VERBOSE));
        results.setLang(lang);
        return results;
    }
    
    private ForgedDarkResults buildResults(List<SingleResult<Integer>> res)
    {
        SingleResultComparator comp = new SingleResultComparator(true);
        res.sort(comp);
        ForgedDarkResults results = new ForgedDarkResults(res);
        SingleResult<Integer> pivotResult = res.get(0);
        if (mods.contains(ForgedDarkModifiers.DESPERATE))
        {
            pivotResult = res.get(res.size()-1);
        }
        if (pivotResult.getValue() >= 6)
        {
            results.setSuccessLevel(2);
        }
        else if (pivotResult.getValue() >= 4)
        {
            results.setSuccessLevel(1);
        }
        else
        {
            results.setSuccessLevel(0);
        }
        if ((res.size() > 1) && !mods.contains(ForgedDarkModifiers.DESPERATE))
        {
            SingleResult<Integer> secondBest = res.get(1);
            if (secondBest.getValue() == 6)
            {
                results.setSuccessLevel(3);
            }
        }
        return results;
    }
}
