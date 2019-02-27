package archetypeAPI.patches;

import archetypeAPI.cards.SilentArchetypeZone;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.TheSilent;
import com.megacrit.cardcrawl.helpers.ModHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

import static archetypeAPI.cards.SilentArchetypeZone.checkSilentArchetypes;
import static archetypeAPI.cards.SilentArchetypeZone.silentArchetypesEnums;

@SpirePatch(
        clz = TheSilent.class,
        method = "getCardPool"
)

public class SilentCardPoolPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )

    public static void insert(TheSilent __instance, @ByRef ArrayList<AbstractCard> tmpPool) {
        // Do thing.
        silentArchetypesEnums.add(SilentArchetypeZone.CardArchsSilentEnum.BASIC);
        silentArchetypesEnums.add(SilentArchetypeZone.CardArchsSilentEnum.POISON);
        tmpPool.clear();
        tmpPool.addAll(checkSilentArchetypes());

    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ModHelper.class, "isModEnabled");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            //  return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
        }
    }
}



