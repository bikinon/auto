package auto;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class drawautoboxcls extends dxf12objects {

  // start attributes
  public String topStyle;
  public String btmStyle;
  
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
 
  public double autoOl = width + 0.5; 
  public double autoLugD = 6;
  public double autoLugW = 48;
  public double lugAng = 1;
  public double autoOlLang = 1; // 3.3deg
  public double autoOlRang = 1; // 75deg  
  public double btmRad = 2;

  public double btm45Rad = 3;
  public double Str45cut = 11;
  public double btmFlap = 0;
  public double p2ang = 0;  // angled cut-back on the rightside of the flap - SEE allowanceSetup
  public double p3ang = 0;  //  - SEE allowanceSetup
  public double lockSlot = 4;
  public double lockSlotAng = 3;
  
  public double topFlap = 0; // needs improvement of B & C
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
  public double bofst = 3; // 020x Bend Offset
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
  
  public boolean breezeReq = false;
  public double breezeS1 = 0;
  public double breezeP1N2 = 0;
  public double breezeP2N2 = 0;
  public double breezeS1min = 0;
  public double breezeS1max = 0;
  public double breezeP2N2min = 0;
  public double breezeP2N2max = 0;
  public String breezeColour = "Black (1000mm Reel)";
  public double breezeReelWdith = 1000;
  
  public String CUT = "CUT";
  public String CREASE = "CREASE";
  public int col = 1; // Line Colour
  public String ltype = "CONTINUOUS"; // Line Type
  public int CW = 2;
  public int CCW = 3;
  
  // end attributes
  
  // start methods
  public String drawautobox() {
    // Set EXTENTS
    //    dxfymax = String.valueOf(wouter * 2 + (dmain * 3));
    //    dxfxmax = String.valueOf((dmain + dblbend + toeflap) * 2 + lmain);
    // DimensionSizes(0.0, 0.0, Double.parseDouble(dxfxmax), Double.parseDouble(dxfymax));
    this.allowanceSetup();
    this.makeupCheck();
    
    dxf += this.dxf_header12();
    
    this.drwbody();
    
    // ******** BOTTOM FLAPS **************************************
    this.absMove(0, 0); // Go back to start do the Btm flap can be called anywhere
    if(this.btmStyle == "0701 Auto Full OL") {
      this.drw0701FullOLbtmFalps(); // Movement is in the Flap sub-routine
      
    } else if(this.btmStyle == "0201 (OFOTB)") {  
      this.Yaxis = -1;
      drw020x();
      this.Yaxis = 1;
      
    } else {
      this.drw0700StdCrashLockBtmFlaps();
    }
    
    // ********* TOP FLAPS ****************************************
    if(this.topStyle == "Hammer Head OL") {
      this.drwHammerheadFlaps();
      
    } else if(this.topStyle == "0215+ P&S / Rippa") {
      this.drw0215ps();
      
    } else if(this.topStyle == "0201 (OFOTB)") {  
      this.absMove(0, this.dotr);
      drw020x();
      
    } else { // "Full Overlap"
      drwOverlapTopFlaps();
      
    } // if
    
    dxf_footer12();
    
    return dxf;    
  }
  
  
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
    this.Line(-FlangeCutBkX, FlangeCutBkY, CUT);
    this.Line(-FlangeChamferX, FlangeChamferY, CUT);
    this.Line(0, this.dotr - this.fcut - FlangeCutBkY - FlangeChamferY, CUT); 
    this.Line(this.flange, this.fcut, CUT);   
    
    this.absMove(0, 0); // Go back to start do the flap can be called anywhere
    
  } // drwbody
  
  
  protected void drw020x() {      
    //this.drwOlFlapP1Top();
    double ac = 2;
    this.angFlapOtr(this.l1, this.topFlap, slot, ac, bofst, slot);
    this.relMove(slot + ac, -(this.topFlap + bofst));
    
    this.ChamFlapInr(this.w2, 2, this.topFlap + bofst, (this.topFlap / 3), 15); 
    
    if (this.Yaxis == -1) {
      //  this.relMove(0, bofst*2); // No idea why
    } else {
      this.relMove(0, -bofst*2); // No idea why
    }
    this.angFlapOtr(this.l3, this.topFlap, slot, ac, bofst, slot);
    
    this.absMove(this.l1 + this.w2 + this.l3, 0);
    if(this.Yaxis == 1) {
      this.relMove(0, this.dotr);
    }
    this.ChamFlapInrP4(this.w4, 2, this.topFlap + bofst, (this.topFlap / 3), 15, 15 - (w2-w4) + 2);
  } // drw020x
  
  
  protected void drwOlFlapP1Top() {
    // PANEL 1 ALSO IN Drw0202HmrHdLil <<<<>>>>>>>>> REPLACE WITH angFlapOtr
    /** Draw 1st Panel Top flap for OL / Hammer head design 
    * TG 21/08/2016 */
    //Line(50, 50, CREASE);
    this.relMove(this.topOtrRad, this.topBndOfst);  
    this.Line(this.l1 - (this.topOtrRad*2), 0, CREASE);
    this.relMove(-this.l1 + this.topOtrRad, -this.topBndOfst);
    
    FilletLines oTmp = new FilletLines();
    double[] arcDat = oTmp.findFilletPoints(this.topP1Ang, this.topFlap, this.topOtrRad);
    
    this.arc2(this.xabs, this.yabs + this.topOtrRad, this.topOtrRad, 270, 360 - arcDat[2], arcDat[1], this.topOtrRad - arcDat[0], CUT);
    
    this.Line(this.topP1Ang, this.topFlap - arcDat[1], CUT);
    this.Line(this.l1 - ((this.topOtrRad + this.topP1Ang) * 2), 0, CUT); 
    
    this.relMove(this.topOtrRad + this.topP1Ang, -this.topFlap);
    this.arc2(this.xabs, this.yabs + this.topOtrRad, this.topOtrRad, 180, 270 - arcDat[2], -arcDat[1], this.topOtrRad - arcDat[0], CUT);
    
    this.Line(-this.topP1Ang, this.topFlap - arcDat[1], CUT); // ERROR in Position after Arc
    
    this.relMove(this.topOtrRad + this.topP1Ang, -this.topFlap);
  } // drwOlFlapP1Top 
  
  
  /**
  *  
  * @param dis - Panel Distance
  * @param flp - Flap Height
  * @param slot - Slot (1/2)
  * @param ac  - Angle Cut
  * @param bof - Bend Offset
  * @param bof - Radius of Fillet
  */
  protected void angFlapOtr(double dis, double flp, double slot, double ac, double bof, double rad) {
    // *** Draw Angled, normally an OUTER with an offset in Depth of flap ***    
    double lbc = 0;
    double flatSide[] = {0,0,0,0};
    if (bof > 0) {
      FilletLines oTmp = new FilletLines();
      lbc = oTmp.findBendXonArc(bof, rad); 
      flatSide = oTmp.findFilletPoints(ac, flp + bof, rad);
      lbc = lbc + (slot - flatSide[3]);
    } else {  
      lbc = slot - rad;
    }
    relMove(lbc, bof);
    Line( dis - (lbc * 2), 0, "CREASE"); // CREASE Line
    relMove( -dis + lbc, -bof); // Re-set Origin Point
    
    FilletLines oFl = new FilletLines(); // Takes mainly Incremental line values
    oFl.absFlatXstr = this.xabs; // Start Point
    oFl.absFlatYstr = this.yabs;    
    oFl.incLineMeetX = slot; 
    oFl.incLineMeetY = 0; 
    oFl.incAngXend = ac; 
    oFl.incAngYend = flp + bof; 
    oFl.radius = rad;
    oFl.dir = "L"; // LEFT SIDE: X axis = 1
    oFl.Yaxis = this.Yaxis; // UP or DOWN
    dxf += oFl.fillet();
    
    this.relMove(slot + ac, flp + bof);
    
    Line(dis - (slot * 2) - (ac * 2), 0, "CUT"); // Top of Flap
    // this.absMove(this.xabs + slot + ac, this.yabs - (flp + bof));
    this.relMove(slot + ac, -(flp + bof)); // Outer edge, Bottom of slot to do the other side
    
    FilletLines oFl2 = new FilletLines();
    oFl2.absFlatXstr = this.xabs;
    oFl2.absFlatYstr = this.yabs;
    oFl2.incLineMeetX = slot; 
    oFl2.incLineMeetY = 0; 
    oFl2.incAngXend = -ac;
    oFl2.incAngYend = flp + bof;
    oFl2.radius = rad;
    oFl2.dir = "R"; // RIGHT SIDE: X axis = -1
    oFl2.Yaxis = this.Yaxis; // UP or DOWN
    dxf += oFl2.fillet();
    
    this.relMove(-(slot + ac), flp + bof);   
    
  } // angflapotr 
  
  
  /**
  * *** Draw Chamfered, Normally INNER flap ***
  * @param dis
  * @param slot - Slot size / 2
  * @param flp  - length of flap
  * @param adfcut - cut back on flap
  * @param aitop - angle size on top
  */
  protected void ChamFlapInr(double dis, double slot, double flp, double adfcut, double aitop) // (CONST vector start, endpnt_Xaxis, CONST Boolean YAxisUpwards,CONST LENGTH slot,flp,adfcut,aitop)
  {
    this.relMove(dis, 0);
    Line( -dis, 0, CREASE);
    
    if(this.Yaxis == -1) {
      this.arc2(this.xabs, this.yabs - slot, slot, 0, 90, slot, -slot, CUT);
    } else {  
      this.arc2(this.xabs, this.yabs + slot, slot, 270, 0, slot, slot, CUT);
    } 
    Line( 0, flp-adfcut-slot, CUT );
    Line(aitop, adfcut, CUT);
    Line(dis-(slot*2)-aitop*2 , 0, CUT);
    Line(aitop,-adfcut, CUT);
    Line(0, -flp+adfcut+slot, CUT);
    
    if(this.Yaxis == -1) {
      this.arc2(this.xabs + slot, this.yabs, slot, 90, 180, slot, slot, CUT);
    } else {
      this.arc2(this.xabs + slot, this.yabs, slot, 180, 270, slot, slot, CUT);
    }
  } // ChamFlapInr  
  
  
  /**
  *  
  * @param dis - Panel Size
  * @param slot - Slot width (1 side)
  * @param flp - Flap Length
  * @param adfcut - Verticle Angle (flp/3)
  * @param aitop - Cut in on top of flap R side
  * @param r4 - Cut in on top of flap L side
  */
  protected void  ChamFlapInrP4(double dis, double slot, double flp, double adfcut, double aitop, double r4) {
    //*** P4 Draw Chamfered, Normally INNER flap ***
    this.relMove(dis, 0);
    Line( -dis, 0, CREASE);
    
    if(this.Yaxis == -1) {
      this.arc2(this.xabs, this.yabs - slot, slot, 0, 90, slot, -slot, CUT);    
    } else {
      this.arc2(this.xabs, this.yabs + slot, slot, 270, 0, slot, slot, CUT);
    }
    
    Line( 0, flp-adfcut-slot, CUT );
    Line(aitop, adfcut, CUT);
    
    Line(dis-slot-aitop-r4 , 0, CUT);
    Line(r4,-adfcut, CUT);
    
    Line(0, -flp+adfcut, CUT);
  } // P4ChamFlapInr
  
  
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
    
    d0215ps.drw0215ps();  
    
  } // Drw0215ps
  
  
  
  
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
    Drw0202HmrHdLil d0202hh = new Drw0202HmrHdLil();
    d0202hh.l1 = this.l1;
    d0202hh.w2 = this.w2;
    d0202hh.l3 = this.l3;
    d0202hh.w4 = this.w4;   
    d0202hh.flange = this.flange;
    d0202hh.dotr = this.dotr;
    
    d0202hh.topFlap = topFlap; // needs improvement of B & C
    
    dxf += d0202hh.drwHammerheadFlaps();
  }
  
  
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
  
  
  
  protected boolean allowanceSetup() {
    // allowances 
    if (flute == "C") {
      l1 = length + 5; // length main
      w2 = width + 5; //
      l3 = length + 5;
      w4 = width + 2;
      dotr = depth + 9; //  
      btmRad = 3;  // Used on all but the 45deg fold as more clearence needed    
      // Auto Full OL flap
      autoOl = width + 0.5; 
      autoLugD = 6;
      lugAng = 1;
      // btm 45deg GL flap
      btm45Rad = 4;
      Str45cut = 11;
      btmFlap = w2 / 2; // Used on Btm panels 2, 3 & 4
      // btm Std Auto Length Flap
      lockSlot = 4;
      lockSlotAng = 3;
      // 0215PS
      t0215sInr = 2;
      t0215sOtr = 4;
      t0215BndOfst = 3;
      t0215DustInSet = 4;
      t0215TuckFlap = this.w2 + 3;    
      
      bofst = 2; // 020x Bend Offset
      slot =  3;
      
    } else if (flute == "B") {
      l1 = length + 3; // length main
      w2 = width + 3; //
      l3 = length + 3;
      w4 = width + 0;
      dotr = depth + 6; // 
      btmRad = 3;  // Used on all but the 45deg fold as more clearence needed    
      // Auto Full OL flap
      autoOl = width + 0.5; 
      autoLugD = 6;
      lugAng = 1;
      // btm 45deg GL flap
      btm45Rad = 4;
      Str45cut = 11;
      btmFlap = w2 / 2; // Used on Btm panels 2, 3 & 4
      // btm Std Auto Length Flap
      lockSlot = 4;
      lockSlotAng = 3;
      // 0215PS
      t0215sInr = 2;
      t0215sOtr = 4;
      t0215BndOfst = 2;
      t0215DustInSet = 3;
      t0215TuckFlap = this.w2 + 2;
      
      bofst = 2; // 020x Bend Offset
      slot =  3;
      
    } else {  // E Flute
      l1 = length + 2; // length main
      w2 = width + 2; //
      l3 = length + 2;
      w4 = width + 0;
      dotr = depth + 4; // 
      btmRad = 2;  // Used on all but the 45deg fold as more clearence needed    
      // Auto Full OL flap
      autoOl = width + 0.5; 
      autoLugD = 6;
      lugAng = 1;
      // btm 45deg GL flap
      btm45Rad = 3;
      Str45cut = 11;
      btmFlap = w2 / 2; // Used on Btm panels 2, 3 & 4
      // btm Std Auto Length Flap
      lockSlot = 4;
      lockSlotAng = 3;
      // 0215PS
      t0215sInr = 1;
      t0215sOtr = 2;
      t0215BndOfst = 2;
      t0215DustInSet = 2;
      t0215TuckFlap = this.w2 + 1.5;
      
      bofst = 2; // 020x Bend Offset
      slot =  3;
    } // end of if-else
    
    // Top Flaps
    if (this.topStyle == "Full Overlap") {
      topFlap = w2;
      topOtrRad = 3;
      topInrRad = 2;   
    } // end of if
    if (this.topStyle == "Hammer Head OL") {
      topFlap = (w2 + 25) / 2;
      topOtrRad = 3;
      topInrRad = 2;   
    } // end of if    
    if (this.topStyle == "0201 (OFOTB)") {
      topFlap = (w2) / 2 + 1;
      topOtrRad = 3;
      topInrRad = 2;   
    } // end of if  
    
    //  FOL Base angles
    double temp1 =  Math.tan(Math.toRadians(3.3));
    double temp2 =  Math.tan(Math.toRadians(18)); //  18 / 72 deg (was 15 / 75 deg) ????
    this.autoOlLang = autoOl * Math.tan(Math.toRadians(3.3)); // 3.3deg
    this.autoOlRang = autoOl * Math.tan(Math.toRadians(18)); //  18 / 72 deg (was 15 / 75 deg)    
    
    autoLugW = (int)(l3 / 6) + 0.5;
    if (autoLugW < 25) {  // Min & Max for base lock lug
      autoLugW = 25;
    } // end of if
    if (autoLugW > 75) {
      autoLugW = 75;
    } // end of if
    
    //  Std Base angles
    //    double temp3 =  Math.tan(Math.toRadians(20));
    //    double temp4 =  Math.tan(Math.toRadians(18));
    if(this.btmStyle == "0701 Auto Full OL") {
      p2ang = btmFlap * Math.tan(Math.toRadians(20)); // 20 / 70 deg
      p3ang = btmFlap * Math.tan(Math.toRadians(18)); //  18 / 72 deg (was 15 / 75 deg)    
    } else if(this.btmStyle == "0700 Auto Std") { // 
      p2ang = btmFlap * Math.tan(Math.toRadians(4)); // 20 / 70 deg
      p3ang = btmFlap * Math.tan(Math.toRadians(6)); //  18 / 72 deg (was 15 / 75 deg)          
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
    
    if (this.topStyle == "Full Overlap" && this.btmStyle == "0701 Auto Full OL") {
      this.s1way = this.topFlap + this.dotr + this.autoOl + this.autoLugD;
    } // end of if
    if (this.topStyle == "Hammer Head OL" && this.btmStyle == "0701 Auto Full OL") {
      this.s1way = this.topFlap + this.dotr + this.autoOl + this.autoLugD;
    } // end of if  
    
    this.n2way = this.flange + this.l1 + this.w2 + this.l3 + this.w4;
    
  }  // calcBlkSz
  
  public void calcBreezePaper() {
    double p2N2 = 0;
    this.calcBlkSz();
    
    this.breezeS1max = this.dotr - this.lockSlot + ((this.w2 + 25) / 2);    
    this.breezeS1min = (this.dotr / 2) - this.lockSlot + ((this.w2 + 25) / 2); 
    if (this.dotr < 250) { // 1st way Paper Size
      this.breezeS1 = this.breezeS1max;
    } else {
      this.breezeS1 = ((int)((this.dotr + 0.5) * 0.6666)) - this.lockSlot + ((this.w2 + 25) / 2); 
    }// end of if
    
    this.breezeP1N2 = this.flange + this.l1 - 10; 
    this.breezeP2N2max = this.w2 + this.l3 + this.w4 - (this.flange + 8);   
    this.breezeP2N2min = this.w2 + this.l3 + (this.w4 / 2);
    
    if (this.breezeColour == "White (1180mm Reel)") {
      this.breezeReelWdith = 1000; //1180; Max cutable size on Kohmann without 2 passes to glue
    } else {
      this.breezeReelWdith = 1000;
    }// end of if
    
    double tmpP2N2 = this.breezeReelWdith - this.breezeP1N2; // Possible problem area
    this.breezeP2N2 = this.breezeP2N2max;
    if (tmpP2N2 > this.breezeP2N2min && tmpP2N2 < this.breezeP2N2max) {
      this.breezeP2N2 = tmpP2N2;
    } // end of if
    
    if((this.breezeP1N2 + this.breezeP2N2) > this.breezeReelWdith) {
      JOptionPane.showMessageDialog(null, "If both sizes added together > 1000 then 2 passes\nmay be required to apply Breeze Paper\n" + String.format("A + B =%10.1f", (this.breezeP1N2 + this.breezeP2N2)), "Breeze Paper Problem", JOptionPane.ERROR_MESSAGE);  
    } // end of if
    if(breezeP2N2min > this.breezeReelWdith) {
      JOptionPane.showMessageDialog(null, "Largest Paper size > 1000 then too big to make on Kohmann\n" + String.format("%10.1f", breezeP2N2min), "Breeze Paper ERROR", JOptionPane.ERROR_MESSAGE);  
    } // end of if 
    
  } // calcBreezePaper
  
  public String calcBnds() {
    
    return "";
  } // calcBnds 
  
  
  
  //protected void flapsize020x(String flapstyle) { 
  ////(CONST STRING flapstyle, VAR LENGTH l,w,folap,filap,da,bof, CONST Integer flute, CONST Boolean OffsetY)
  //
  //// ****************************
  //// calc flap sizes 22/8/97 - TG
  //// ****************************
  //double fa = 0;  // flap allowance
  //double fr = 0;  // flap rounding figure
  //double temp = 0;
  //
  //  if (flute == "E") { // Set standard allowances   
  //    da=1;
  //    fa=1; 
  //    fr=1; 
  //    bof=1;
  //  } else if (flute == "B") { // B flute
  //    da=1.5;
  //    fa=1; 
  //    fr=1; 
  //    bof=2; 
  //  } else if (flute == "C") { // C flute
  //    da=2.5;
  //    fa=3; 
  //    fr=-1; 
  //    bof=3; 
  //  } else if (flute == "BC") { // BC flute
  //    da=3.5;
  //    fa=5; 
  //    fr=-1; 
  //    bof=5; 
  //  } 
  //
  //  if (OffsetY==false) {
  //    bof=0;
  //  }
  //
  ////{*** Style Allowances ***}
  //  if (flapstyle == "0200 (Raw Edge)") {
  //    da = 0;
  //    folap = 0;
  //  bof=0; 
  //  } else if (flapstyle == "0201-00 (O.F.O.T.B.)") {
  //  folap=(this.width /2)+fa;                          
  //  if (this.width /2 != Math.ceil(this.width /2)) {                      
  //    folap=((this.width +fr)/2)+fa;
  //  }
  //  filap=folap+bof; // Inner flap size
  //
  //  } else if (flapstyle == "0202-00 (O.F.O.)") {
  //    // GetInput("0202-00 Outer Flap Overlap", temp);
  //      // a jframe here isn't strictly necessary, but it makes the example a little more real
  //      JFrame frame = new JFrame("InputDialog Example #1");
  //      // prompt the user to enter their name
  //      temp = Double.parseDouble(JOptionPane.showInputDialog(frame, "0202-00 Outer Flap Overlap"));
  //
  //    folap=((this.width +temp)/2)+fa;                          
  //    if ((this.width +temp)/2 != Math.ceil((this.width +temp)/2)) {                        
  //      folap = (((this.width  + temp) + fr) / 2) + fa;
  //    }
  //    filap = folap + bof; // Inner flap size
  //
  //  } else if (flapstyle == "0203-00 (O.F.F.O.)") {
  //    if (flute == "E") { // E flute   
  //      da = 3;
  //    folap = this.width ;
  //    bof=2; 
  //    } else if (flute == "B") {  // B flute
  //      da = 4.5;
  //    folap = this.width ;
  //    bof=3; 
  //    } else if (flute == "C") {  // C flute
  //      da = 7;
  //    folap = this.width ;
  //    bof=4; 
  //    } else if (flute == "BC") {  // BC flute
  //      da = 11;
  //    folap = this.width +2;
  //    bof=6; 
  //    }
  //    if (OffsetY==false) {
  //    bof=0;
  //    }
  //    filap=folap+bof; // Inner flap size
  //
  //  } else if (flapstyle == "0204-00 (A.F.M.)") {
  //    folap=(this.width /2)+fa;                          
  //    if (this.width /2 != Math.ceil(this.width /2)) {                      
  //      folap=((this.width +fr)/2)+fa;
  //    } 
  //    filap=(l/2)+fa;                          
  //    if (this.length/2 != Math.ceil(this.length/2)) {                      
  //      filap=((this.length+fr)/2)+fa;
  //    }
  //
  //  } else if (flapstyle == "0204-01 (Canners)") {
  //    folap=(this.width /2)+fa;                          
  //    if (this.width /2 != Math.ceil(this.width /2)) {                      
  //      folap=((this.width +fr)/2)+fa;
  //    }
  //    JFrame frame = new JFrame("InputDialog Example #1");
  //    temp = Double.parseDouble(JOptionPane.showInputDialog(frame, "0204-01 (R) Inner flap increase over Outer flap"));
  //    filap=folap+temp;                          
  //
  //  } else if (flapstyle == "0205-00 (I.F.M.)") {
  //    folap=(this.length/2)+fa;                          
  //    if (this.length/2 != Math.ceil(this.length/2)) {                      
  //      folap=((this.length+fr)/2)+fa;
  //    }
  //    filap=folap+bof; // Inner flap size
  //
  //  } else if (flapstyle == "0209-01 (T.F.)") {
  //    folap=65;
  //    JFrame frame = new JFrame("InputDialog Example #1");
  //    folap = Double.parseDouble(JOptionPane.showInputDialog(frame, "0209-01: Short Flap (65mm std size)"));
  //    if (flute == "C") {
  //      folap = folap+2;
  //    }
  //    filap=folap+bof; // Inner flap size
  //
  //  } else if (flapstyle == "0209-02 (O.F.G.)") {
  //    temp=6;
  //    JFrame frame = new JFrame("InputDialog Example #1");
  //    temp = Double.parseDouble(JOptionPane.showInputDialog(frame, "0209-02 Outer Flaps Gap"));
  //    folap=((this.width -temp)/2)+fa;                          
  //    if ((this.width -temp)/2 != Math.ceil((this.width -temp)/2)) {
  //      folap=(((this.width -temp)+fr)/2)+fa;
  //    }
  //    filap=folap+bof; // Inner flap size
  //
  //  } // *** Style Allowances ***
  //} // *** flapsize
  
  
  
  // end methods
  
  
}