package ChatterMod.actions;
/*     */ import ChatterMod.MainModfile;
/*     */ import Zhenghuo.modcore.ExampleMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
import java.util.function.Consumer;
/*     */ import javax.sound.sampled.*;
/*     */
/*     */
/*     */
/*     */
/*     */

/*     */
/*     */ public class RecordAndPlaybackAction extends AbstractGameAction {
/*     */   private TargetDataLine input;
/*     */  public static int volume = 0;
/*     */   public RecordAndPlaybackAction(Consumer<Float> callback) {
/*  17 */     this.audioFile = new File("ChatterMod_Chatter.wav");
/*     */ 
/*     */     
/*  20 */     this.duration = this.startDuration = Settings.ACTION_DUR_XLONG;
/*  21 */     this.callback = callback;
/*     */   }
/*     */   private final File audioFile; private final Consumer<Float> callback;
/*     */   
/*     */   public void update() {
/*  26 */     if (this.duration == this.startDuration) {
                ExampleMod.Tips="录音中";
                ExampleMod.StartRecord=true;
/*  27 */       (new CaptureThread()).start();
/*     */     }
/*  29 */     tickDuration();
/*  30 */     if (this.isDone) {
/*  31 */       if (this.input != null) {
/*  32 */         this.input.stop();
/*  33 */         this.input.close();
/*     */       } 
/*  35 */       addToTop(new AbstractGameAction()
/*     */           {
/*     */             public void update() {
/*  38 */               AbstractDungeon.effectsQueue.add(new ShockWaveEffect((ChatterMod.util.Wiz.adp()).hb.cX, (ChatterMod.util.Wiz.adp()).hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC));
/*  39 */               RecordAndPlaybackAction.this.callback.accept(Float.valueOf(RecordAndPlaybackAction.this.getAvgVolume(RecordAndPlaybackAction.this.audioFile)));
/*  40 */               RecordAndPlaybackAction.this.play(RecordAndPlaybackAction.this.audioFile);

/*  41 */               this.isDone = true;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getAvgVolume(File file) {
/*     */     try {
/*  49 */       AudioInputStream ais = AudioSystem.getAudioInputStream(file);
/*  50 */       long length = ais.getFrameLength();
/*  51 */       if (length <= 0L) {
/*  52 */         return 0.0F;
/*     */       }
/*  54 */       byte[] bytes = new byte[(int)length * 2];
/*  55 */       int numBytes = ais.read(bytes);
/*  56 */       float total = 0.0F;
/*  57 */       for (int i = 0; i < numBytes; i += 2) {
/*  58 */         int audioVal = bytes[i + 1] << 8 | bytes[i] & 0xFF;
/*  59 */         total = (float)(total + Math.pow(audioVal, 2.0D));
/*     */       } 
/*     */       
/*  62 */       total /= (float)length;
/*  63 */       total = (float)(Math.log10(total) * 10.0D);
/*     */ 
/*     */       
/*  66 */       ais.close();
/*  67 */       return total;
/*  68 */     } catch (UnsupportedAudioFileException | IOException e) {
/*  69 */       MainModfile.logger.error("Could not load file:");
/*  70 */       e.printStackTrace();
/*     */       
/*  72 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */   public void play(File file) {
/*     */     try {
/*  77 */       Clip clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
/*     */       
/*  79 */       clip.addLineListener(event -> {
/*  80 */             if (event.getType() == LineEvent.Type.STOP) {
/*  81 */               clip.close();
/*     */             }
/*     */           });
/*  84 */       clip.open(AudioSystem.getAudioInputStream(file));
/*  85 */       clip.start();
/*     */     }
/*  87 */     catch (Exception e) {
/*  88 */       MainModfile.logger.error("Could not load file:");
/*  89 */       e.printStackTrace(System.out);
/*     */     } 
/*     */   }
/*     */   
/*     */   private class CaptureThread extends Thread { private CaptureThread() {}
/*     */     
/*     */     public void run() {
/*  96 */       AudioFormat format = new AudioFormat(32000.0F, 16, 1, true, false);
/*     */       try {
/*  98 */         RecordAndPlaybackAction.this.input = AudioSystem.getTargetDataLine(format);
/*  99 */         AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
/* 100 */         RecordAndPlaybackAction.this.input.open(format);
/* 101 */         RecordAndPlaybackAction.this.input.start();
/* 102 */         AudioSystem.write(new AudioInputStream(RecordAndPlaybackAction.this
/* 103 */               .input), fileType, RecordAndPlaybackAction.this
/*     */             
/* 105 */             .audioFile);
/* 106 */       } catch (LineUnavailableException e) {
/* 107 */         MainModfile.logger.error("No recording device detected:");
/* 108 */         e.printStackTrace();
/* 109 */       } catch (IOException e) {
/* 110 */         MainModfile.logger.error("Could not write to file:");
/* 111 */         e.printStackTrace();
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\gaoming\Desktop\游戏\ChatterMod.jar!\ChatterMod\actions\RecordAndPlaybackAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */