package auto;

public class drw0203ps extends dxf12objects {

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
  
  
  protected String drwOverlapTopFlaps() {
    // Full Overlap Top Flaps with PS & Rippa
      this.drwOlFlapP1Top();
      
      // P2 Flap
      this.drwFullOlTopP2Flap(this.w2 / 15, this.topFlap / 3);
      
      // P3 Flap
      this.absMove(this.l1 + this.w2, this.dotr);
      this.drwFullOlOtrFlap();
      
      // P4 Flap
      this.drwFullOlTopP4Flap(this.w2 / 15, this.topFlap / 3);
      
      // PS & Rippa
      this.relMove(-(this.w4 + this.l3) + (this.w2 / 15) + 19, -6);
      double PsRippaDis = (this.l3 - (19*2));
      this.Line(PsRippaDis, 0, "Annotation");
      this.relMove(0, -12);
      this.Line(-PsRippaDis, 0, "Annotation");
      this.relMove(0, -6);
      this.Line(PsRippaDis, 0, "Annotation");
      this.relMove(0, -6);
      this.Line(-PsRippaDis, 0, "Annotation");
      
      TextInsert((PsRippaDis / 2) - 30, 15, "Peel & Seal", "Annotation"); // 30 is a fudge for Text Width not Calculated
      TextInsert((PsRippaDis / 2) - 30, 1, "Rippa Tape", "Annotation");
      
      return dxf;
  } // drwOverlapTopFlaps

  
  protected void drwOlFlapP1Top() {
    // PANEL 1 ALSO IN drw0202HmrHd
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
    
    
  protected void drwFullOlOtrFlap() {
    //  PANEL 3
    double topPSbit = 56.5;
    double slot = 10;
    
    FilletLines oTmp = new FilletLines();
    double[] arcDat = oTmp.findFilletPoints(this.topP1Ang, this.topFlap, this.topOtrRad);
    double XcenterAbs = this.xabs;
    double YcenterAbs = this.yabs + this.topOtrRad;
    double XendPt = arcDat[1];
    double YendPt = this.topOtrRad - arcDat[0];
    double startAngle = 270; // arcDat2[2]; // 0;
    double endAngle = 360 - arcDat[2]; //arcDat2[3]; // 0;
    double tab = 30;
    double atab = 16.5;
    double rad = 12;
    
    this.relMove(this.topOtrRad, this.topBndOfst);
    this.Line(this.l3 - this.topOtrRad*2, 0, CREASE);
    this.absMove(this.l1 + this.w2, this.dotr);
    // this.relMove(-this.l3, 0);
    
    this.arc2(XcenterAbs, YcenterAbs, this.topOtrRad, startAngle, endAngle, XendPt, YendPt, CUT);
    
    this.Line(this.topP1Ang, this.topFlap - YendPt - topPSbit, CUT);
    this.Line(2, 13.5, CUT);
    this.Line(0, 6.5, CUT);
    
    this.relMove(tab, 6);
    this.Line(-tab, -6, "SAFETY");
    this.arc2(this.xabs, this.yabs + (atab/2), atab / 2, 90, 270, 0, atab, "SAFETY");  // Tab arc
    this.Line(tab, 0, "SAFETY");
    double tmpTabX = this.xabs;  // top point of arc ********
    double tmpTabY = this.yabs;
    this.relMove(-tab, 0);
    
    this.Line(0, 20 - rad, CUT);
    this.arc2(this.xabs + rad, this.yabs, rad, 90, 180, rad, rad, CUT);
    // Right side
    this.absMove(this.l1 + this.w2 + this.l3, this.dotr);
    this.arc2(this.xabs, this.yabs + this.topOtrRad, this.topOtrRad, 180 - arcDat[2], 270, -XendPt, YendPt, CUT);
    double tmpX = this.xabs;  // top point of arc ********
    double tmpY = this.yabs;
    
    this.Line(-this.topP1Ang, this.topFlap - YendPt - topPSbit, CUT);
    this.Line(-2, 13.5, CUT);
    this.Line(0, 6.5, CUT);
    
    this.relMove(-tab, 6);
    this.Line(tab, -6, "SAFETY");
    this.arc2(this.xabs, this.yabs + (atab/2), atab / 2, 270, 90, 0, atab, "SAFETY"); // Tab Arc
    this.Line(-tab, 0, "SAFETY");
    this.relMove(tab, 0);
    
    this.Line(0, 20 - rad, CUT);
    this.arc2(this.xabs - rad, this.yabs, rad, 0, 90, -rad, rad, CUT);  // ERROR in Position after Arc 
    //  this.Line(this.xabs - tmpX, 0, CUT);
    this.Line(-this.l3 + (19*2), 0, CUT); // **** Fudge - fix later *****
    
    // Perf Line
    this.absMove(tmpTabX + 10, tmpTabY);
    this.Line(this.l3 - ((7+30+10) * 2), 0, "PERF6");
    
  } // drwFullOlOtrFlap
    
    
  protected void drwFullOlTopP2Flap(double fxAngCut, double fyAngCut) {
      
    this.absMove(this.l1, this.dotr);
    this.Line(this.w2, 0, CREASE);
    this.absMove(this.l1, this.dotr);
    this.arc2(this.xabs, this.yabs + topInrRad, topInrRad, 270, 0, topInrRad, topInrRad, CUT);
    
    this.Line(0, this.topFlap - topInrRad - fyAngCut, CUT);
    this.Line(fxAngCut, fyAngCut, CUT);
    this.Line(this.w2 - ((topInrRad + fxAngCut)*2), 0, CUT);
    
    this.absMove(this.l1 + this.w2, this.dotr);
    this.arc2(this.xabs, this.yabs + topInrRad, topInrRad, 180, 270, -topInrRad, topInrRad, CUT);
    
    this.Line(0, this.topFlap - topInrRad - fyAngCut, CUT);
    this.Line(-fxAngCut, fyAngCut, CUT);
} // drwFullOlTopP2Flap

  
protected void drwFullOlTopP4Flap(double fxAngCut, double fyAngCut) {
      
    this.absMove(this.l1 + this.w2 + this.l3, this.dotr);
    this.Line(this.w4, 0, CREASE);
    this.absMove(this.l1 + this.w2 + this.l3, this.dotr);
    this.arc2(this.xabs, this.yabs + topInrRad, topInrRad, 270, 0, topInrRad, topInrRad, CUT);
    
    this.Line(0, this.topFlap - topInrRad - fyAngCut, CUT);
    this.Line(fxAngCut, fyAngCut, CUT);
    this.Line(this.w4 - (topInrRad + (fxAngCut*2)), 0, CUT);
    
    this.absMove(this.l1 + this.w2 + this.l3 + this.w4, this.dotr);
    
    this.Line(0, this.topFlap - fyAngCut, CUT);
    this.Line(-fxAngCut, fyAngCut, CUT);
} // drwFullOlTopP4Flap
      
        
} // Class drw0203ps
