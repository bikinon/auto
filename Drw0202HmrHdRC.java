package auto;

public class Drw0202HmrHdRC extends dxf12objects {

  public String topStyle; 
  public double l1 = 0; // length main
  public double w2 = 0; //
  public double l3 = 0;
  public double w4 = 0;
  public double flange = 0; 

  public double dotr = 4;
  
  public double topFlap = 0; // needs improvement of B & C
  public double topOtrRad = 3;
  public double topInrRad = 2;
  public double topP1Ang = 5 - topOtrRad; // (Includes topOtrRad)
  public double topP2Ang = 11 - topInrRad; // (Includes topInrRad)
  public double topBndOfst = 3; 
  
  public String CUT = "CUT";
  public String CREASE = "CREASE";
  
// Hammer Head Variables ********************
  private double slitOfst = 1.5;
  private double bndOfst = 2;
  private double hmrStrt = 82; // Straight section of the Hammer Head
  private double hmrWidth = 35; // Width of the Hammer Head Strap
  private double tabLen = 15; // Length of Ripper Tab Before Chamfer angle
  private double tabRipWidth = 10; // Straight section of the Tab Verticle
  private double tabLenAng = 4; 
  private double chmfrAng = 3; // Chamfer Angle at the end of the Ripper Tab
  private double tabWidth = tabRipWidth + (tabLenAng * 2) - (chmfrAng * 2); // Length of Ripper Tab - Chamfer angle to finish
  private double chmfrTop = 2; // Top 45 deg comtinuation of tab
  private double hmrWidthAng = 5; // Final Top inset angle
// ******************************    

  
  
   protected void drwHH() {
   /* Draw RC version of Hammer Head
    Start at Left side bottom of the flap and draw slit as well as Hammer Head */
    this.Line(-slitOfst, bndOfst, CUT);
    this.Line(0, topFlap - (bndOfst + hmrWidth), CUT);
    this.relMove(slitOfst, 0);
    this.Line(-hmrStrt, 0, CUT);
    
    this.Line(-tabLen, -tabLenAng, CUT); // Start drawing the Rippa Tab
    this.Line(-chmfrAng, chmfrAng, CUT);
    this.Line(0, tabWidth, CUT);
    this.Line(chmfrAng, chmfrAng, CUT);
    this.Line(tabLen, -tabLenAng, CUT); // Finish drawing the Rippa Tab
    this.relMove(-tabLen, tabLenAng); 
    
    this.Line(chmfrTop, chmfrTop, CUT);
    this.Line(hmrWidthAng, hmrWidth - (tabRipWidth + tabLenAng + chmfrTop), CUT);
    this.Line(hmrStrt + tabLen - (chmfrTop + hmrWidthAng), 0, CUT);
    this.Line( 0, -hmrWidth, CREASE);
    
    this.relMove(0, -(topFlap - hmrWidth)); // Move to Bottom of flap
   // drwHH ---------- 
   }
   
  
  protected String drwHammerheadFlaps() {
    double fxAngCut = (this.w2 / 15), fyAngCut = this.topFlap / 3;
  
  // Top Bends
    this.absMove(0, this.dotr);
    this.Line(this.l1, 0, CREASE);
    this.Line(this.w2, 0, CREASE);
    this.Line(this.l3, 0, CREASE);
    this.Line(this.w4, 0, CREASE);
    
  // Draw Top Flaps
  // P1 Top Flap
    this.absMove(0, this.dotr); // Move to the top of the flange
    this.Line(topOtrRad, 0, CUT);
    this.Line(topP2Ang, topFlap, CUT);
    this.Line(this.l1 - ((topOtrRad + topP2Ang) * 2), 0, CUT);
    this.Line(topP2Ang, -topFlap, CUT);
    this.Line(topOtrRad, 0, CUT);
    
    // P2 Slot
    double tmpX = this.xabs; 
    double tmpY = this.yabs;
    this.Line(topInrRad, 0, CUT);
    this.Line(0, this.topFlap - fyAngCut, CUT);
    this.Line(fxAngCut, fyAngCut, CUT);
    this.Line(this.w2 - fxAngCut - (hmrStrt + tabLen - (chmfrTop + hmrWidthAng)), 0, CUT); // Just to get an OL
    this.absMove(tmpX, tmpY);
    
    this.relMove(this.w2, 0);
    drwHH();
   
    this.relMove(0, topFlap);
    this.Line(this.l3, 0, CUT);
    
    this.relMove(0, -topFlap);
    this.Xaxis = -1;
    drwHH();
    this.Xaxis = 1;
    
    this.relMove(this.w4, 0);
    this.Line(0, this.topFlap - fyAngCut, CUT);
    this.Line(-(fxAngCut - 3), fyAngCut, CUT);
    
    this.Line(-(this.w4 - (fxAngCut - 3) - (hmrStrt + tabLen - (chmfrTop + hmrWidthAng))), 0, CUT);
    
    return dxf;
  // drwHammerheadFlaps -----------  
  }  
   
   
  
  protected String drwHammerheadFlaps_Lil() {
  // Top Bends
    this.absMove(0, this.dotr);
    this.Line(this.l1, 0, CREASE);
    this.Line(this.w2, 0, CREASE);
    this.Line(this.l3, 0, CREASE);
    this.Line(this.w4, 0, CREASE);
    
    // Draw Top Flaps
    this.absMove(0, this.dotr); // Move to the top of the flange
    this.drwOlFlapP1Top();
    
    // P2 Top Flap - One side as connects to HammerHead 
    this.absMove(this.l1, this.dotr);
    double wSideRad = 2;
    double lSideRad = 3;
    double startAngle = 270, endAngle = 0;
    double XcenterAbs = this.xabs, YcenterAbs = this.yabs + 2;
    double XendPt = 2, YendPt = 2;
    double fxAngCut = (this.w2 / 15), fyAngCut = this.topFlap / 3;
    this.arc2(XcenterAbs, YcenterAbs, wSideRad, startAngle, endAngle, XendPt, YendPt, CUT);
    
    this.Line(0, this.topFlap - wSideRad - fyAngCut, CUT);
    this.Line(fxAngCut, fyAngCut, CUT);
    this.Line(this.w2 - wSideRad - fxAngCut - 42, 0, CUT);
    
    // HammerHead P3
    this.absMove(this.l1 + this.w2, this.dotr);
    this.relMove(23, this.topFlap - 35);
    double tmpX = this.xabs;  // Hook slot cut
    double tmpY = this.yabs;    
    this.drwHH_Lil();
    
    // HH Slot Left
    this.absMove(this.l1 + this.w2, this.dotr);
    this.Line(lSideRad, 0, CUT);
    this.Line(1.5, this.topFlap - 35, CUT);
    this.absMove(this.l1 + this.w2, this.dotr);
    XcenterAbs = this.xabs;
    YcenterAbs = this.yabs + 2;
    startAngle = 180;
    endAngle = 270;
    XendPt = -2;
    YendPt = 2;
    double wSideHhVrt = this.topFlap - 35 - 0.7970;
    double wSideHhVrt3 = (wSideHhVrt / 3);
    this.arc2(XcenterAbs, YcenterAbs, wSideRad, startAngle, endAngle, XendPt, YendPt, CUT);
    this.Line(0, wSideHhVrt - wSideHhVrt3, CUT);  // 2.5039 angle of HH
    this.Line(-3.5, wSideHhVrt3  - 2.5806, CUT); //  2.5039
    // HH Slot Right
    this.absMove(this.l1 + this.w2 + this.l3, this.dotr);
    this.Line(-lSideRad, 0, CUT); // Btm of Slot
    this.Line(-1.5, this.topFlap - 35, CUT);
    this.absMove(this.l1 + this.w2 + this.l3, this.dotr);
    this.arc2(this.xabs, this.yabs + 2, wSideRad, 270, 0, 2, YendPt, CUT);
    this.Line(0, wSideHhVrt - wSideHhVrt3, CUT);  // 2.5039 angle of HH - Right Slot, Right Side
    this.Line(3.5, wSideHhVrt3  - 2.5806, CUT); //  2.5039 - Slot Angle Out 
    // P4 edge Top Flap 
    this.absMove(this.l1 + this.w2 + this.l3 + this.w4, this.dotr);
    this.Line(0, this.topFlap - fyAngCut, CUT);
    this.Line(-fxAngCut, fyAngCut, CUT);
    this.Line(-this.w4 + wSideRad + fxAngCut + 40, 0, CUT);
    
    // Last part of P3 & Right Hammerhead 
    this.absMove(tmpX - 13, tmpY + 10);
    this.Line(this.l3 - 20, 0, "PERF6");
    
    this.relMove(-13, -10);
    this.Xaxis = -1;
    this.drwHH_Lil();
    this.Xaxis = 1;
    this.absMove(this.l1 + this.w2, this.dotr + this.topFlap);
    this.Line(this.l3, 0, CUT);
    
    return dxf;
  } // drwHammerheadFlaps
    

    
  protected void drwHH_Lil() {
    /* Draw Hammer head
    Fixed Sizes for speed - probably bite me later! */
    // Start at Straight Rip part of Hammer head Left side:
    this.Line(-20, 0, CUT);
    this.Line(-43, -7, CUT);
    this.Line(0, (42 - 18.5), CUT);
    this.Line(-2, 18.5, CUT);
    
    this.relMove(42, 0);
    
    this.Line(-35, 0, CUT);
    this.Line(0, -19.8, CUT);
    double tmpX = this.xabs;  // Hook slot cut
    double tmpY = this.yabs;
    this.Line(-5, (19.8-18.5), CUT); // Btm of Slot
    
    this.relMove(40, 16);
    this.Line(0, -23, CUT);
    this.Line(0, -9.9884, CREASE); // 10.48
    
    this.absMove(tmpX, tmpY);
    this.Line(20, -(25-19.8), CUT); // Angle on HmrHd Tab
    this.Line(5, 0, CUT);
    
    // Btm Slot
    this.relMove(42, -50);
    double wSideRad = 2;
    double startAngle, endAngle;
    double lSideRad = 3;
    double lSideAng = 3; // added to lSideRad
    double hmrCutBk = 0.8023;
    //      this.arc2(XcenterAbs, YcenterAbs, wSideRad, startAngle, endAngle, XendPt, YendPt, CUT); 
  } // drwH   
  
  
  protected void drwOlFlapP1Top() {
    // PANEL 1 ALSO IN Drw0202HmrHdLil
    /** Draw 1st Panel Top flap for OL / Hammer head design 
    * TG 21/08/2016 */
    String errorMsg = "";
    if (this.topStyle == "Full Overlap") {
      this.absMove(0 + this.topOtrRad, this.dotr  + this.topBndOfst);  
    }
    this.Line(this.l1 - (this.topOtrRad*2), 0, CREASE);
    this.absMove(0, this.dotr);
    
    FilletLines oTmp = new FilletLines();
    double[] arcDat = oTmp.findFilletPoints(this.topP1Ang, this.topFlap, this.topOtrRad);
    
    this.arc2(this.xabs, this.yabs + this.topOtrRad, this.topOtrRad, 270, 360 - arcDat[2], arcDat[1], this.topOtrRad - arcDat[0], CUT);
    
    this.Line(this.topP1Ang, this.topFlap - arcDat[1], CUT);
    this.Line(this.l1 - ((this.topOtrRad + this.topP1Ang) * 2), 0, CUT); 
    
    this.absMove(this.l1, this.dotr);
    this.arc2(this.xabs, this.yabs + this.topOtrRad, this.topOtrRad, 180, 270 - arcDat[2], -arcDat[1], this.topOtrRad - arcDat[0], CUT);
    
    this.Line(-this.topP1Ang, this.topFlap - arcDat[1], CUT); // ERROR in Position after Arc

  } // drwOlFlapP1Top 
  
  
} // Class Drw0202HmrHdLil
