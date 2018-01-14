package auto;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class DrawDCcase extends dxf12objects {

  // start attributes
  public String topStyle;
  public String btmStyle;
  public boolean offsetBends = true;
  
  public double length = 0;
  public double width = 0;
  public double depth = 0;

  public double l1 = length + 2; // length main
  public double w2 = width - 2; //
  public double l3 = width + 2;
  public double w4 = width + 0;
  public double flange = 28;
  public double fcut = 7;
  public double dotr = depth + 4; //
  public double stdDepthAllow = 0;

  public double topFlap = 0; // needs improvement of B & C
  public double btmFlap = 0;
  public double topInrFlap = 0; // Not yet used
  public double btmInrFlap = 0; // Not yet used

  // 0701 Full OL Btm
  public double autoOl = width + 0.5; 
  public double autoLugD = 6;
  public double autoLugW = 48;
  public double lugAng = 1;
  public double autoOlLang = 1; // 3.3deg
  public double autoOlRang = 1; // 75deg  
  public double btmRad = 2;

  public double btm45Rad = 3;
  public double p2ang = 0;  // angled cut-back on the rightside of the flap - SEE allowanceSetup
  public double p3ang = 0;  //  - SEE allowanceSetup
  public double lockSlot = 4;
  public double lockSlotAng = 3;
  
  
  public double topOtrRad = 3;
  public double topInrRad = 2;
  public double topP1Ang = 5 - topOtrRad; // (Includes topOtrRad)
  public double topP2Ang = 11 - topInrRad; // (Includes topInrRad)
  public double topBndOfst = 3;
  // 0215PS
  public double t0215sInr = 1; // Flap Side Inner
  public double t0215sOtr = 2;
  public double t0215BndOfst = 2;
  public double t0215DustInSet = 2;
  public double t0215TuckFlap = this.w2 + 1.5;
  public double tuckTabPs = 65;
// 020x
  public double bofst = 3;
  public double bofstTop = 3; // 020x Bend Offset
  public double bofstBtm = 3; // 020x Bend Offset
  public double slot =  3;
// Fillet Vals
//  public double flatXstr = 0;
//  public double flatYstr = 0;
//  public double flatXend = 0; 
//  public double flatYend = 0; 
//  public double angXstr = 0; // Must be the same as flatXend
//  public double angYstr = 0; // Must be the same as flatYend
//  public double angXend = 0; 
//  public double angYend = 0;   
  
  public String flute = "E";

  public double s1way = 0;
  public double n2way = 0;  
  
  public String CUT = "CUT";
  public String CREASE = "CREASE";
  public int col = 1; // Line Colour
  public String ltype = "CONTINUOUS"; // Line Type
  public int CW = 2;
  public int CCW = 3;
  
  // end attributes
  
  // start methods
  public String drawCase() {
    // Set EXTENTS
    //    dxfymax = String.valueOf(wouter * 2 + (dmain * 3));
    //    dxfxmax = String.valueOf((dmain + dblbend + toeflap) * 2 + lmain);
    // DimensionSizes(0.0, 0.0, Double.parseDouble(dxfxmax), Double.parseDouble(dxfymax));
    this.allowanceSetup();
    calcBlkSz();
    
    MachineSz mc = new MachineSz();
        mc.s1wayNet = this.s1way;
        mc.n2wayNet = this.n2way;
        mc.flange = this.flange;
        mc.l1 = l1;
        mc.w2 = w2;
        mc.l3 = l3;
        mc.w4 = w4;
        mc.btmFlap = this.btmFlap;
        mc.topFlap = this.topFlap;
        mc.pnlDepth = this.dotr;
        mc.desStyle = "";
    mc.checkLimits();
    
    this.makeupCheck();
    
    dxf += this.dxf_header12();
    
    this.drwbody();
    
    // ******** BOTTOM FLAPS **************************************
    this.absMove(0, 0); // Go back to start do the Btm flap can be called anywhere
    this.bofst = this.bofstBtm;
    if(this.btmStyle == "0701 Auto Full OL") {
      this.drw0701FullOLbtmFalps(); // Movement is in the Flap sub-routine
      
    } else if(this.btmStyle == "0701 Auto Full OL RC") {  
      this.drw0701FullOLbtmFalpsRC();
      
    } else if(this.btmStyle == "0700 Auto Std") {  
      this.drw0700StdCrashLockBtmFlaps();
      
    } else if(this.btmStyle == "0216 (Envelope Base)") {  
      this.drw0216EnvelopeBtmFlaps();
      
    } else { // this.btmStyle == "0201 (OFOTB)")
      this.Yaxis = -1;
      drw02xx(this.btmFlap, this.btmInrFlap);
      this.Yaxis = 1;
      
    }
    
    // ********* TOP FLAPS ****************************************
    this.bofst = this.bofstTop;
    if(this.topStyle == "Hammer Head OL") {
      this.drwHammerheadFlaps();
      
    } else if(this.topStyle == "0215+ P&S / Rippa RC") {
      this.drw0215ps();

    } else if(this.topStyle == "0215+ P&S / Rippa") {
      this.drw0215psFP1();
           
    } else if(this.topStyle == "Full Overlap") {  // "Full Overlap"
      drwOverlapTopFlaps();
      
    } else { 
      this.absMove(0, this.dotr);
      drw02xx(this.topFlap, this.topInrFlap);
      
    } // if
    
    dxf_footer12();
    
    return dxf;    
  } // drawCase
  
  
  
  protected void drwbody() {
    double FlangeChamferX = 5;
    double FlangeChamferY = 10;
    double FlangeCutBkX = this.flange - FlangeChamferX;
    double FlangeCutBkY = 25;
    
    // Start Btm of Flange
    this.Line(0, this.dotr, CREASE);
    this.relMove(this.l1, 0);
    this.Line(0, -this.dotr, CREASE);
    this.relMove(this.w2, 0);
    this.Line(0, this.dotr, CREASE);
    this.relMove(this.l3, 0);
    this.Line(0, -this.dotr, CREASE);
    this.relMove(this.w4, 0);
    this.Line(0, this.dotr, CUT);
    this.absMove(0, 0);
    //    this.Line(-this.flange, this.flange, CUT);
    //    this.Line(0, this.dotr - this.fcut - this.flange, CUT); 
    //    this.Line(this.flange, this.fcut, CUT);
    /* New shaped flange for Martin */
    if (this.btmStyle == "0701 Auto Full OL" || this.btmStyle == "0700 Auto Std" || this.btmStyle == "0701 Auto Full OL RC") {
      this.Line(-FlangeCutBkX, FlangeCutBkY, CUT);
      this.Line(-FlangeChamferX, FlangeChamferY, CUT);
      this.Line(0, this.dotr - this.fcut - FlangeCutBkY - FlangeChamferY, CUT); 
      this.Line(this.flange, this.fcut, CUT);   
    } else {
      this.Line(-this.flange, this.fcut, CUT);  
      this.Line(0, this.dotr - this.fcut * 2, CUT); 
      this.Line(this.flange, this.fcut, CUT);  
    }
    
    this.absMove(0, 0); // Go back to start do the flap can be called anywhere
    
  } // drwbody
  
  
  
  //  protected void drwOlFlapP1Top(double flap) {
  //    // PANEL 1 ALSO IN Drw0202HmrHdLil <<<<>>>>>>>>> REPLACE WITH angFlapOtr
  //    /** Draw 1st Panel Top flap for OL / Hammer head design 
  //    * TG 21/08/2016 */
  ////Line(50, 50, CREASE);
  //    this.relMove(this.topOtrRad, this.topBndOfst);  
  //    this.Line(this.l1 - (this.topOtrRad*2), 0, CREASE);
  //    this.relMove(-this.l1 + this.topOtrRad, -this.topBndOfst);
  //    
  //    FilletLines oTmp = new FilletLines();
  //    double[] arcDat = oTmp.findFilletPoints(this.topP1Ang, flap, this.topOtrRad);
  //    
  //    this.arc2(this.xabs, this.yabs + this.topOtrRad, this.topOtrRad, 270, 360 - arcDat[2], arcDat[1], this.topOtrRad - arcDat[0], CUT);
  //    
  //    this.Line(this.topP1Ang, flap - arcDat[1], CUT);
  //    this.Line(this.l1 - ((this.topOtrRad + this.topP1Ang) * 2), 0, CUT); 
  //    
  //    this.relMove(this.topOtrRad + this.topP1Ang, -flap);
  //    this.arc2(this.xabs, this.yabs + this.topOtrRad, this.topOtrRad, 180, 270 - arcDat[2], -arcDat[1], this.topOtrRad - arcDat[0], CUT);
  //    
  //    this.Line(-this.topP1Ang, flap - arcDat[1], CUT); // ERROR in Position after Arc
  //
  //    this.relMove(this.topOtrRad + this.topP1Ang, -flap);
  //  } // drwOlFlapP1Top 
  
  
  protected void drw0215ps() {
    Drw0215ps d0215ps = new Drw0215ps();
    
    d0215ps.l1 = this.l1;
    d0215ps.w2 = this.w2;
    d0215ps.l3 = this.l3;
    d0215ps.w4 = this.w4;   
    d0215ps.flange = this.flange;
    d0215ps.dotr = this.dotr;
    
    d0215ps.t0215sInr = t0215sInr; // Flap Side Inner
    d0215ps.t0215sOtr = t0215sOtr;
    d0215ps.t0215BndOfst = t0215BndOfst;
    d0215ps.t0215DustInSet = t0215DustInSet;
    d0215ps.t0215TuckFlap = t0215TuckFlap;
    d0215ps.tuckTabPs = tuckTabPs;  
    
    dxf += d0215ps.drw0215psFP3();  
    
  } // Drw0215ps
  
 
  protected void drw0215psFP1() {
    Drw0215ps d0215ps = new Drw0215ps();
    
    d0215ps.l1 = this.l1;
    d0215ps.w2 = this.w2;
    d0215ps.l3 = this.l3;
    d0215ps.w4 = this.w4;   
    d0215ps.flange = this.flange;
    d0215ps.dotr = this.dotr;
    
    d0215ps.t0215sInr = t0215sInr; // Flap Side Inner
    d0215ps.t0215sOtr = t0215sOtr;
    d0215ps.t0215BndOfst = t0215BndOfst;
    d0215ps.t0215DustInSet = t0215DustInSet;
    d0215ps.t0215TuckFlap = t0215TuckFlap;
    d0215ps.tuckTabPs = tuckTabPs;  
    
    dxf += d0215ps.drw0215psFP1();  
    
  } // Drw0215ps
  
  
  protected void drw02xx(double flap, double inrFlap) {
    Drw020x oDrw020x = new Drw020x();
    
    oDrw020x.l1 = this.l1;
    oDrw020x.w2 = this.w2;
    oDrw020x.l3 = this.l3;
    oDrw020x.w4 = this.w4;    
    oDrw020x.flange = this.flange;
    oDrw020x.dotr = this.dotr;  
    oDrw020x.bofst = this.bofst;
    oDrw020x.slot = this.slot;
    
    oDrw020x.xabs = this.xabs;
    oDrw020x.yabs = this.yabs;
    oDrw020x.Xaxis = this.Xaxis;
    oDrw020x.Yaxis = this.Yaxis;
    
    dxf += oDrw020x.drwFlaps(flap, inrFlap);
    
  } // drw02xx
  
  
  
  protected void drwOverlapTopFlaps() {
    // Full Overlap Top Flaps with PS & Rippa
    drw0203ps d0203ps = new drw0203ps();
    d0203ps.topStyle = topStyle;    
    d0203ps.l1 = this.l1;
    d0203ps.w2 = this.w2;
    d0203ps.l3 = this.l3;
    d0203ps.w4 = this.w4;   
    d0203ps.flange = this.flange;
    d0203ps.dotr = this.dotr;
    
    d0203ps.topFlap = topFlap; // needs improvement of B & C
    d0203ps.topOtrRad = topOtrRad;
    d0203ps.topInrRad = topInrRad;
    d0203ps.topP1Ang = topP1Ang; // (Includes topOtrRad)
    d0203ps.topP2Ang = topP2Ang; // (Includes topInrRad)
    d0203ps.topBndOfst = topBndOfst;
    
    dxf += d0203ps.drwOverlapTopFlaps();
  } // drwOverlapTopFlaps
  
  
  protected void drwHammerheadFlaps() {
    Drw0202HmrHdRC d0202hh = new Drw0202HmrHdRC();
    d0202hh.l1 = this.l1;
    d0202hh.w2 = this.w2;
    d0202hh.l3 = this.l3;
    d0202hh.w4 = this.w4;   
    d0202hh.flange = this.flange;
    d0202hh.dotr = this.dotr;
    
    d0202hh.topFlap = topFlap; // needs improvement of B & C
    
    dxf += d0202hh.drwHammerheadFlaps();
  } // drwHammerheadFlaps
  
  
  protected void drw0701FullOLbtmFalps() {
    // Expects to start at 0,0  
    // Draw 0701 full OL Crashlock Bottom Flaps
    drw0701 d0701 = new drw0701();
    d0701.l1 = this.l1;
    d0701.w2 = this.w2;
    d0701.l3 = this.l3;
    d0701.w4 = this.w4;   
    d0701.flange = this.flange;
    d0701.btmFlap = this.btmFlap;
    d0701.btm45Rad = this.btm45Rad;
    d0701.p2ang = this.p2ang;
    
    d0701.autoOl = autoOl; 
    d0701.autoLugD = autoLugD;
    d0701.autoLugW = autoLugW;
    d0701.lugAng = lugAng;
    d0701.autoOlLang = autoOlLang; // 3.3deg
    d0701.autoOlRang = autoOlRang; // 75deg  
    d0701.btmRad = btmRad;
    d0701.lockSlot = lockSlot;
    d0701.lockSlotAng = lockSlotAng;
    
    dxf += d0701.drw0701FullOLbtmFalps();
  } // drw0701FullOLbtmFalps
    
  
  
  protected void drw0701FullOLbtmFalpsRC() {
    // Expects to start at 0,0  
    // Draw 0701 full OL Crashlock Bottom Flaps
    drw0701 d0701 = new drw0701();
    d0701.l1 = this.l1;
    d0701.w2 = this.w2;
    d0701.l3 = this.l3;
    d0701.w4 = this.w4;   
    d0701.flange = this.flange;
    d0701.btmFlap = this.btmFlap;
    d0701.btm45Rad = this.btm45Rad;
    d0701.p2ang = this.p2ang;
    
    d0701.autoOl = autoOl; 
    d0701.autoLugD = autoLugD;
    d0701.autoLugW = autoLugW;
    d0701.lugAng = lugAng;
    d0701.autoOlLang = autoOlLang; // 3.3deg
    d0701.autoOlRang = autoOlRang; // 75deg  
    d0701.btmRad = btmRad;
    d0701.lockSlot = lockSlot;
    d0701.lockSlotAng = lockSlotAng;
    
    dxf += d0701.drw0701FullOLbtmFalpsRC();
  } // drw0701FullOLbtmFalps  
  
  
  protected void drw0700StdCrashLockBtmFlaps() {
    // Expects to start at 0 0 
    // Draw Std 0700 Crashlock Bottom Flaps
    drw0700 d0700 = new drw0700();
    d0700.l1 = this.l1;
    d0700.w2 = this.w2;
    d0700.l3 = this.l3;
    d0700.w4 = this.w4;   
    d0700.flange = this.flange;
    d0700.btmFlap = this.btmFlap;
    d0700.btm45Rad = this.btm45Rad;
    d0700.p2ang = this.p2ang;
    
    dxf += d0700.drw0700StdCrashLockBtmFlaps();   
  } // drw0700StdCrashLockBtmFlaps

  
  protected void drw0216EnvelopeBtmFlaps() {
    // Expects to start at 0 0 
    // Draw Std 0216 Bottom Flaps
    Drw0216 d0216 = new Drw0216();
    d0216.l1 = this.l1;
    d0216.w2 = this.w2;
    d0216.l3 = this.l3;
    d0216.w4 = this.w4;   
    d0216.flange = this.flange;
    d0216.btmFlap = this.btmFlap;
    d0216.tab = 25;
    d0216.slot = 2;
    
    dxf += d0216.drw00216btmFalps();   
  } // drw0216EnvelopeBtmFlaps  
  
  
  protected boolean allowanceSetup() {
    double daTop = 0;
    double daBtm = 0;
    // allowances 
    if (flute.equals("C") || flute.equals("EB")) {
      l1 = length + 5; // length main
      w2 = width + 5; //
      l3 = length + 5;
      w4 = width + 2;
      stdDepthAllow = 9; //  
      topOtrRad = 3; // Top outer flap slot base radius
      topInrRad = 2; // Top inner flap slot base radius      
      btmRad = 3;  // Used on all but the 45deg fold as more clearence needed    
      // Auto Full OL flap - See style0701AutoFullOL for Sizes & Allowances
      // btm 45deg GL flap
      btm45Rad = 3;
      //       btmFlap= w2 / 2; // Used on Btm panels 2, 3 & 4
      // btm Std Auto Length Flap
      lockSlot = 6;
      lockSlotAng = 3;
      // 0215PS
      t0215sInr = 2;
      t0215sOtr = 4;
      t0215BndOfst = 3;
      t0215DustInSet = 4;
      t0215TuckFlap = this.w2 + 3;    
      
      bofstTop = 3; // 020x Bend Offset
      bofstBtm = 3;
      slot =  3;
      
    } else if (flute.equals("B")) {
      l1 = length + 3; // length main
      w2 = width + 3; //
      l3 = length + 3;
      w4 = width + 0;
      stdDepthAllow = 6;      
//      dotr = depth + 6; // 
      topOtrRad = 3;
      topInrRad = 2;      
      btmRad = 3;  // Used on all but the 45deg fold as more clearence needed    
      // Auto Full OL flap - See style0701AutoFullOL for Sizes & Allowances
      // btm 45deg GL flap
      btm45Rad = 3;
      //     btmFlap = w2 / 2; // Used on Btm panels 2, 3 & 4
      // btm Std Auto Length Flap
      lockSlot = 5;
      lockSlotAng = 3;
      // 0215PS
      t0215sInr = 2;
      t0215sOtr = 4;
      t0215BndOfst = 2;
      t0215DustInSet = 3;
      t0215TuckFlap = this.w2 + 2;
      
      bofstTop = 2; // 020x Bend Offset
      bofstBtm = 2;
      bofst = 2; // 020x Bend Offset
      slot =  3;
      
    } else  if (flute.equals("BC")) {
      l1 = length + 7; // length main
      w2 = width + 7; //
      l3 = length + 7;
      w4 = width + 3;
      stdDepthAllow = 16; //  
      topOtrRad = 4; // Top outer flap slot base radius
      topInrRad = 3; // Top inner flap slot base radius      
      btmRad = 3;  // Used on all but the 45deg fold as more clearence needed    
      // Auto Full OL flap - See style0701AutoFullOL for Sizes & Allowances
      // btm 45deg GL flap
      btm45Rad = 3;
      //       btmFlap= w2 / 2; // Used on Btm panels 2, 3 & 4
      // btm Std Auto Length Flap
      lockSlot = 7;
      lockSlotAng = 3;
      // 0215PS
      t0215sInr = 3;
      t0215sOtr = 4;
      t0215BndOfst = 4;
      t0215DustInSet = 5;
      t0215TuckFlap = this.w2 + 4;    
      
      bofstTop = 5; // 020x Bend Offset
      bofstBtm = 5;
      slot =  4;
      
    } else {  // E Flute
      l1 = length + 2; // length main
      w2 = width + 2; //
      l3 = length + 2;
      w4 = width + 0;
      stdDepthAllow = 4;
//      dotr = depth + 4; // 
      topOtrRad = 3;
      topInrRad = 2;      
      btmRad = 2;  // Used on all but the 45deg fold as more clearence needed    
      // Auto Full OL flap - See style0701AutoFullOL for Sizes & Allowances
      // btm 45deg GL flap
      btm45Rad = 2;
      //      btmFlap = w2 / 2; // Used on Btm panels 2, 3 & 4
      // btm Std Auto Length Flap
      lockSlot = 4;
      lockSlotAng = 3;
      // 0215PS
      t0215sInr = 1;
      t0215sOtr = 2;
      t0215BndOfst = 2;
      t0215DustInSet = 2;
      t0215TuckFlap = this.w2 + 1.5;
      
      bofstTop = 2; // 020x Bend Offset
      bofstBtm = 2;
      bofst = 2; // 020x Bend Offset
      slot =  2;
    } // end of if-else
    
    // Top Flaps
    //    if (this.topStyle == "Full Overlap") {
    //      topFlap = w2;
    //      topOtrRad = 3;
    //      topInrRad = 2;   
    //    } // end of if
    //    if (this.topStyle == "Hammer Head OL") {
    //      topFlap = (w2 + 25) / 2;
    //      topOtrRad = 3;
    //      topInrRad = 2;   
    //    } // end of if    
    //    if (this.topStyle == "0201 (OFOTB)") {
    //        topFlap = (w2) / 2 + 1;
    //        topOtrRad = 3;
    //        topInrRad = 2;   
    //    } // end of if  
    
    //  FOL Base angles
    //    this.autoOlLang = autoOl * Math.tan(Math.toRadians(3.3)); // 3.3deg
    //    this.autoOlRang = autoOl * Math.tan(Math.toRadians(18)); //  18 / 72 deg (was 15 / 75 deg)    
    //    
    //    autoLugW = (int)(l3 / 6) + 0.5;
    //    if (autoLugW < 25) {  // Min & Max for base lock lug
    //      autoLugW = 25;
    //    } // end of if
    //    if (autoLugW > 75) {
    //      autoLugW = 75;
    //    } // end of if
    
    //  Std Base angles
    //    if(this.btmStyle == "0701 Auto Full OL") {
    //      p2ang = btmFlap * Math.tan(Math.toRadians(20)); // 20 / 70 deg
    //      p3ang = btmFlap * Math.tan(Math.toRadians(18)); //  18 / 72 deg (was 15 / 75 deg)    
    //    } else if(this.btmStyle == "0700 Auto Std") { // 
    //      p2ang = btmFlap * Math.tan(Math.toRadians(4)); // 20 / 70 deg
    //      p3ang = btmFlap * Math.tan(Math.toRadians(6)); //  18 / 72 deg (was 15 / 75 deg)          
    //    }
    
    // *** TOP FLAPS ***
    CalcFlapSz oFlps = new CalcFlapSz();
    oFlps.flute = this.flute;
    oFlps.length = this.length;
    oFlps.width = this.width;
    oFlps.flapEx = 0; //flapEx; // Flap overlap or Gap, etc
    oFlps.flapstyle = this.topStyle; 
    // 0215PS / 0215
    oFlps.t0215TuckFlap = this.t0215TuckFlap;
    // FullOverlapPS & HaammerHeadOL 
    oFlps.topOtrRad = this.topOtrRad;
    oFlps.topInrRad = this.topInrRad;
    
    oFlps.flapsize(w2, l3);
    // <<< Returned Values <<<<<<<
    this.topFlap = oFlps.otrFlap; //  Outer flap size
    this.topInrFlap = oFlps.inrFlap;
    //this.topFlap = oFlps.inrFlap; // Inner flap size
    bofstTop = oFlps.bofst; // = this.bofst; // 020x Bend Offset
    daTop = oFlps.da;    //da - part depth allowance
    
    // *** BTM FLAPS ***
    oFlps.flapEx = 0; //flapEx; // Flap overlap or Gap, etc
    oFlps.flapstyle = this.btmStyle;  
    // Auto Full OL flap
    oFlps.btmFlap = this.btmFlap; // Used on Btm panels 2, 3 & 4     
    oFlps.flapsize(w2, l3);
    // <<< Returned Values <<<<<<<
    this.btmFlap = oFlps.otrFlap; // Std Flap length
    this.btmInrFlap = oFlps.inrFlap; // Not currenly used - bofst used
    
    if (this.btmStyle.equals("0701 Auto Full OL") || (this.btmStyle == "0701 Auto Full OL RC")) {
      this.btmFlap = oFlps.btmFlap; //  panels 2,3&4 flap size
    }
    // Are there straight depth Bends?
    if (this.topStyle.equals("Hammer Head OL")) { 
       offsetBends = false; 
    }
    this.autoOl = oFlps.autoOl; // width + 0.5;     
    this.autoLugD = oFlps.autoLugD;
    this.lugAng = oFlps.lugAng;
    this.autoOlLang = oFlps.autoOlLang;
    this.autoOlRang = oFlps.autoOlRang;
    this.autoLugW = oFlps.autoLugW;
    this.p2ang = oFlps.p2ang;
    this.p3ang = oFlps.p3ang;
    
    //this.btmFlap = oFlps.inrFlap; // Inner flap size
    bofstBtm = oFlps.bofst; // = 0; // 020x Bend Offset
    daBtm = oFlps.da;    //da - part depth allowance
    
    if (offsetBends == false) { // does not have offset bends
        this.dotr = this.depth + stdDepthAllow;
    } else {
        this.dotr = daTop + bofstTop + this.depth + daBtm + bofstBtm;
    }
    
    return true; 
  }  // allowanceSetup
  
  
  
  protected void makeupCheck() {
    
    //    if (psflap > winr) {
    //      javax.swing.JOptionPane.showMessageDialog(null,
    //      "Error: Peel & Seal Flap > Width\nFlap will overlap the base of the box and not seal", "Error Message",
    //      javax.swing.JOptionPane.ERROR_MESSAGE);
    //    } // end of if  
    //    if (psflap < 42) {
    //      javax.swing.JOptionPane.showMessageDialog(null,
    //      "Error: 42mm is ABSOLUTE MINIMUM for P&S Flap\nMake up problems likely even at 42mm, 54mm recommended minimum size.", "Error Message",
    //      javax.swing.JOptionPane.ERROR_MESSAGE);
    //    } // end of if 
  } // makeupCheck
  
  
  protected void plantLimitsCheck() {
    
  }  
  
  public void calcBlkSz() {
    allowanceSetup();
    makeupCheck();
    
    if (this.topStyle == "Full Overlap" && (this.btmStyle == "0701 Auto Full OL") || (this.btmStyle == "0701 Auto Full OL RC")) {
      this.s1way = this.topFlap + this.dotr + this.autoOl + this.autoLugD;
    } // end of if
    if (this.topStyle == "Hammer Head OL" && (this.btmStyle == "0701 Auto Full OL") || (this.btmStyle == "0701 Auto Full OL RC")) {
      this.s1way = this.topFlap + this.dotr + this.autoOl + this.autoLugD;
    } // end of if  
    
    this.n2way = this.flange + this.l1 + this.w2 + this.l3 + this.w4;
    
  }  // calcBlkSz
  
  
  public String calcBnds() {
    
    return "";
  } // calcBnds 
  
  
  
  
  // end methods
  
  
}