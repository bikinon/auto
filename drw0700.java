package auto;

public class drw0700 extends dxf12objects {
/*
Std 25mm OL Crashlock bottom design    
*/
  public double l1 = 0; // length main
  public double w2 = 0; //
  public double l3 = 0;
  public double w4 = 0;
  public double flange = 0;
  public double btmFlap = 0;    
  public double btm45Rad = 3;
  public double p2ang = 0;  // angled cut-back on the rightside of the flap - SEE allowanceSetup
    

  public String CUT = "CUT";
  public String CREASE = "CREASE";  
  
  protected String drw0700StdCrashLockBtmFlaps() {
    // Expects to start at 0 0 
    // Draw Std 0700 Crashlock Bottom Flaps
    drw0700StdCrashLockBtmP13(1);
    this.relMove(this.l1 + this.w2, 0);
    this.drw0700StdCrashLockBtmP24(2);
    drw0700StdCrashLockBtmP13(3);
    this.relMove(this.l3 + this.w4, 0);
    this.drw0700StdCrashLockBtmP24(4);
    
    return dxf;
  } // drw0700StdCrashLockBtmFlaps
      

  protected void drw0700StdCrashLockBtmP13(int panel) {
      double panelLen = 0; 
      double topAngCutIn = Math.cos(Math.toRadians(45)) * 10; //10mm 45deg cutout section;
      double flangeCutIn = this.flange + 5; // use 5mm on top of flange for now 
      double fishtailRdctn = 2; // Reduction over and above the 3mm Left edge cut in to allow for Fishtailing on the machine
      double RightFlapCutIn = topAngCutIn - fishtailRdctn; // want More than 3mm due to fishtailing so 5mm(2mm reduction from the backward movement)
      double lftIn = 1;
      double flapOL = 25;
      double dwnLck = 4;
      
      if (panel == 1) {
        panelLen = this.l1;
      } else {
        panelLen = this.l3;
      }  // end of if
      
      /* Start Right side with 45 deg bend section*/
      this.relMove(this.l3, 0);
      this.Line(-this.btm45Rad, 0, CUT);
      
      this.Line(-(topAngCutIn), -topAngCutIn, this.CUT);
      this.Line(-(this.btmFlap - topAngCutIn), -this.btmFlap + topAngCutIn, "CUTCRE6");
      this.relMove(this.btmFlap - topAngCutIn, this.btmFlap - topAngCutIn);
      this.Line(RightFlapCutIn, -RightFlapCutIn, this.CUT); // Right Angle - Moving away from the 45 deg Cut & Bend intersection
      
      this.Line(0, -(this.btmFlap + flapOL - (topAngCutIn + RightFlapCutIn)), CUT);
      this.Line(-(this.btmFlap - flapOL - (topAngCutIn - RightFlapCutIn)), 0, CUT);
      this.Line(-flapOL, flapOL, CUT);
      
      this.Line(-(panelLen/2 - (this.btmFlap + this.btm45Rad)) , 0, CUT);
      this.Line( 0, -dwnLck, CUT);
      this.Line( -flapOL + dwnLck, -flapOL + dwnLck, CUT);
      
      this.Line(-(panelLen/2 - (flapOL - dwnLck) - this.p2ang), 0, CUT);
//        this.Line(-(panelLen - this.btm45Rad - fishtailRdctn - this.p2ang), 0, CUT);   // Flat section at Btm of flap
      
//      if (panel == 1) {
//        this.Line(-this.p2ang, this.btmFlap + flapOL, CUT);
//      } else { // 3
        this.Line(-(this.p2ang - lftIn), this.btmFlap + flapOL, CUT);
        Line(-lftIn, 0, CUT);
//      }
        //
        this.Line(panelLen - this.btm45Rad, 0, CREASE);
        this.relMove(-(panelLen - this.btm45Rad), 0); // Back to Start

  } // drw0700StdCrashLockBtmP13
  
 
  protected void drw0700StdCrashLockBtmP24(int panel) {
    double leftInSet = 2;
    double rightInset = 2;
    double rightAngCutIn = Math.cos(Math.toRadians(45)) * this.btmFlap; // 45deg cut away;
    double leftAngCutIn = Math.cos(Math.toRadians(85)) * this.btmFlap; // 5 degrees
    double panelLen = this.w4;
    
    if (panel == 2) {
      panelLen = this.w2;
      Line(-rightInset, 0, CUT); // We want an Inset on panel 2 
    } else {
      rightInset = 0; // Not required for Panel 4
    } // if
     
    Line(-this.btmFlap, -this.btmFlap, CUT);
    Line(-(panelLen - (rightInset + this.btmFlap + leftInSet + leftAngCutIn) ), 0, CUT);
    Line(-leftAngCutIn, this.btmFlap, CUT);
    Line(-leftInSet, 0, CUT);
    
    Line(panelLen, 0, CREASE);
    
  } // drw0700StdCrashLockBtmP24










} // drw0700
