package archetypeAPI.patches;

import archetypeAPI.util.cardpoolClearance;
import basemod.abstracts.CustomPlayer;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

import static archetypeAPI.archetypes.abstractArchetype.UsedArchetypesCombined;

@SpirePatch(
        clz = CustomPlayer.class,
        method = "getCardPool"
)

public class BasemodCardPoolPatch {

    @SpirePrefixPatch
    public static SpireReturn<ArrayList<AbstractCard>> Prefix(CustomPlayer __instance, ArrayList<AbstractCard> tmpPool) {
        if (!UsedArchetypesCombined.isEmpty()) {
            ArrayList<AbstractCard> testPool = new ArrayList<>(tmpPool);

            cardpoolClearance.replaceCardpool(tmpPool, UsedArchetypesCombined);

            if (!UsedArchetypesCombined.isEmpty()) {
                testPool.removeIf(card -> {
                            boolean idCheckBool = false;
                            for (AbstractCard c : tmpPool) {
                                if (card.cardID.equals(c.cardID)) {
                                    idCheckBool = true;
                                }
                            }
                            return idCheckBool;
                        }
                );
                if (!testPool.isEmpty()) {
                    for (AbstractCard c : testPool) {
                        System.out.println("You missed a couple: ");
                        System.out.println("Name: " + c.name + " ID: " + c.cardID);
                        System.out.println("(This list excludes starter deck cards.)");
                    }
                }
            }

            System.out.println("Archetype API Log: Custom Character card pool patch. You are playing with: " + tmpPool.size() + " cards.");
            System.out.println("These cards are: " + tmpPool.toString());

            return SpireReturn.Return(tmpPool);
        }
            return SpireReturn.Continue();

    }

}


