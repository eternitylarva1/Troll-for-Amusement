package Zhenghuo.patchs;

import Zhenghuo.utils.Calculate;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.beyond.AwakenedOne;
import extendedui.EUIRM;
import extendedui.ui.controls.EUITextBoxInput;
import extendedui.ui.hitboxes.EUIHitbox;

import java.util.ArrayList;
import java.util.Objects;

import static extendedui.ui.EUIBase.scale;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "<class>"
)
public class MonsterAddFieldsPatch {
    private static final EUITextBoxInput Inputers = (EUITextBoxInput) new EUITextBoxInput(EUIRM.images.rectangularButton.texture(),
            new EUIHitbox(0, 0, scale(320), scale(40)).setIsPopupCompatible(true))
            ;
    private static final Object[] questions = Calculate.generateMathQuestion();
    public static SpireField<EUITextBoxInput> f_Inputers = new SpireField<>(() -> Inputers);
    public static SpireField<Object[]> f_questions = new SpireField<>(() -> questions);


    public MonsterAddFieldsPatch() {

    }

}
