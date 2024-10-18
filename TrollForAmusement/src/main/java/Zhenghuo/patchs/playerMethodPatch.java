package Zhenghuo.patchs;

import Zhenghuo.actions.FusionAction;
import Zhenghuo.card.CharacterCard;
import Zhenghuo.card.TongpeiCard;
import Zhenghuo.otherplayer.OtherPlayerHelper;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static Zhenghuo.actions.FusionAction.cardResult;
import static Zhenghuo.actions.GatherCharacterAction.result;


public class playerMethodPatch {




    @SpirePatch(
            cls = "com.megacrit.cardcrawl.characters.AbstractPlayer",
            method = "renderPlayerBattleUi"
    )
    public static class RenderPatch {
        public RenderPatch() {
        }

        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer _instance, SpriteBatch sb) {

            switch (AbstractDungeon.getCurrRoom().phase) {
                case COMBAT:
                    if (OtherPlayerHelper.hasMinions(AbstractDungeon.player)) {
                       OtherPlayerHelper.getMinions().render(sb);
                    }
            }
        }

    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.screens.select.HandCardSelectScreen",
            method = "render"
    )
    public static class RenderPatch1 {
        public RenderPatch1() {
        }

        @SpirePostfixPatch
        public static void Postfix(HandCardSelectScreen _instance, SpriteBatch sb) {

           if(cardResult!=null){
               cardResult.render(sb);
           }
        }


    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.screens.select.GridCardSelectScreen",
            method = "render"
    )
    public static class GridRenderPatch1 {
        public GridRenderPatch1() {
        }

        @SpirePostfixPatch
        public static void Postfix(GridCardSelectScreen _instance, SpriteBatch sb) {

            if(cardResult!=null){
                cardResult.render(sb);
            }
        }


    }

    private static float convertX(float x) {
        return x * 235.0F * Settings.scale-2*235.0F * Settings.scale + 640.0F * Settings.scale;
    }

    private static float convertY(float y) {
        return y * -235.0F * Settings.scale + 850.0F * Settings.scale;
    }
    @SpirePatch(
            cls = "com.megacrit.cardcrawl.screens.select.HandCardSelectScreen",
            method = "update"
    )
    public static class updatePatch {
        public updatePatch() {
        }
        private static int num=0;
        @SpirePostfixPatch
        public static void Postfix(HandCardSelectScreen _instance) {
            AbstractCard c;
            if(cardResult!=null){
                cardResult.update();
            }
        if(num!=_instance.selectedCards.size()){
            num=_instance.selectedCards.size();
            Iterator var4 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
            ArrayList<Character> ChList = new ArrayList<>();
            int upgradenum=0;
            while(var4.hasNext()) {
                c = (AbstractCard)var4.next();


                if(!(c instanceof TongpeiCard)){

                    System.out.println("正在尝试组合"+c.name);
                    for (char ch : c.name.toCharArray()) {
                        if(!(ch == "+".charAt(0)||Character.isDigit(ch))) {
                            ChList.add(ch);
                            System.out.println("已将"+ch+"加入检索序列");
                        }
                        else if(Character.isDigit(ch)){
                            int number = ch - '0'-1;
                            System.out.println("检测到数字"+number+"转换成升级");

                            upgradenum+=number;

                        }else{
                            upgradenum++;
                        }


                    }
                }
                else{
                    System.out.println("检测到名字为通配符，改为*");
                    ChList.add("*".charAt(0));
                }
            }
            AbstractCard result;
            List<AbstractCard> result1=result(ChList);
            if(!result1.isEmpty())
            {

                result= result1.get(AbstractDungeon.cardRandomRng.random(0,result1.size()-1)).makeSameInstanceOf();

            }
            else{
                StringBuilder sb = new StringBuilder();
                for (char s : ChList) {
                    sb.append(s);
                }
                result=new CharacterCard( sb.toString());
            }


            cardResult=result;
            num=AbstractDungeon.handCardSelectScreen.selectedCards.size();
            cardResult.target_x=convertX(1);
            cardResult.target_y=convertY(3);
            cardResult.name=cardResult.originalName;
            cardResult.applyPowers();
            System.out.println("已经成功改变");
        }

        }


    }

    @SpirePatch(
            cls = "com.megacrit.cardcrawl.screens.select.GridCardSelectScreen",
            method = "update"
    )
    public static class GridupdatePatch {
        public GridupdatePatch() {
        }
        private static int num=0;
        @SpirePostfixPatch
        public static void Postfix(GridCardSelectScreen _instance) {
            AbstractCard c;
            if(cardResult!=null){
                cardResult.update();
            }
            if(num!=_instance.selectedCards.size()){
                num=_instance.selectedCards.size();
                Iterator var4 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();
                ArrayList<Character> ChList = new ArrayList<>();
                int upgradenum=0;
                while(var4.hasNext()) {
                    c = (AbstractCard)var4.next();


                    if(!(c instanceof TongpeiCard)){

                        System.out.println("正在尝试组合"+c.name);
                        for (char ch : c.name.toCharArray()) {
                            if(!(ch == "+".charAt(0)||Character.isDigit(ch))) {
                                ChList.add(ch);
                                System.out.println("已将"+ch+"加入检索序列");
                            }
                            else if(Character.isDigit(ch)){
                                int number = ch - '0'-1;
                                System.out.println("检测到数字"+number+"转换成升级");

                                upgradenum+=number;

                            }else{
                                upgradenum++;
                            }


                        }
                    }
                    else{
                        System.out.println("检测到名字为通配符，改为*");
                        ChList.add("*".charAt(0));
                    }
                }
                AbstractCard result;
                List<AbstractCard> result1=result(ChList);
                if(!result1.isEmpty())
                {

                    result= result1.get(AbstractDungeon.cardRandomRng.random(0,result1.size()-1)).makeSameInstanceOf();

                }
                else{
                    StringBuilder sb = new StringBuilder();
                    for (char s : ChList) {
                        sb.append(s);
                    }
                    result=new CharacterCard( sb.toString());
                }


                cardResult=result;
                num=AbstractDungeon.gridSelectScreen.selectedCards.size();
                cardResult.target_x=convertX(1);
                cardResult.target_y=convertY(3);
                cardResult.name=cardResult.originalName;
                cardResult.applyPowers();
                System.out.println("已经成功改变");
            }

        }


    }

}
