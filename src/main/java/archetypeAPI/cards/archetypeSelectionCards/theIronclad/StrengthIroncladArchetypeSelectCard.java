package archetypeAPI.cards.archetypeSelectionCards.theIronclad;

import archetypeAPI.archetypes.theIronclad.BasicIronclad;
import archetypeAPI.archetypes.theIronclad.StrengthIronclad;
import archetypeAPI.cards.AbstractArchetypeCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import static archetypeAPI.patches.ArchetypeCardTags.SINGLE;

public class StrengthIroncladArchetypeSelectCard extends AbstractArchetypeCard {

    public static final String ID = "archetypeAPI:StrengthIroncladArchetypeSelectCard";

    public static final String IMG = "archetypeAPIResources/images/cards/demon_form.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("archetypeAPI:FlavorIronclad");

    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

    public static final CardColor COLOR = CardColor.RED;
    public static final CardType TYPE = CardType.POWER;

    public StrengthIroncladArchetypeSelectCard() {
        super(ID, NAME, IMG, DESCRIPTION, TYPE, COLOR);
        tags.add(SINGLE);
    }

    @Override
    public void archetypeEffect() {
        StrengthIronclad strengthIronclad = new StrengthIronclad();
    }

    @Override
    public String getTooltipName() {
        return FLAVOR_STRINGS[0];
    }

    @Override
    public String getTooltipDesc() {
        return FLAVOR_STRINGS[4];
    }

}