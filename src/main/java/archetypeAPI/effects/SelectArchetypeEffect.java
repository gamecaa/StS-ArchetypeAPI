package archetypeAPI.effects;

import archetypeAPI.archetypes.AbstractArchetype;
import archetypeAPI.cards.AbstractArchetypeCard;
import archetypeAPI.jsonClasses.UiStrings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static archetypeAPI.archetypes.AbstractArchetype.cardsOfTheArchetypesInUse;
import static archetypeAPI.util.CardpoolMaintenance.makeSureWeMeetMinimum;

public class SelectArchetypeEffect extends AbstractGameEffect {
    private boolean cardsWereUsed;
    private boolean openedGridScreen;
    private String gridSelectText;
    private boolean needReinst = false;

    public SelectArchetypeEffect() {
        this.duration = Settings.ACTION_DUR_FAST;
        cardsWereUsed = false;
        openedGridScreen = false;

        InputStream in = AbstractArchetype.class.getResourceAsStream("/archetypeAPIResources/localization/eng/gridSelect-Strings.json");
        UiStrings gridSelectText = new Gson().fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), UiStrings.class);
        this.gridSelectText = gridSelectText.TEXT;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.openedGridScreen && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                tickDuration();
            } else if (!openedGridScreen) {
                cardsOfTheArchetypesInUse.clear();
                CardGroup cardg = AbstractArchetype.getArchetypeSelectCards(AbstractDungeon.player.chosenClass);
                AbstractDungeon.gridSelectScreen.open(cardg, Integer.MAX_VALUE, true, gridSelectText);
                this.openedGridScreen = true;
            }
        } else {
            if (!cardsWereUsed) {
                if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                    for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                        if (c instanceof AbstractArchetypeCard) {
                            ((AbstractArchetypeCard) c).archetypeEffect();
                        }
                    }
                }

                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    c.stopGlowing();
                }

                makeSureWeMeetMinimum();

                if (!cardsOfTheArchetypesInUse.isEmpty()) {
                    CardCrawlGame.dungeon.initializeCardPools();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                cardsWereUsed = true;
            }

            tickDuration();
        }
    }

    public void tickDuration() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
    }

    @Override
    public void dispose() {
    }
}
