package auto;

public class drw0701 extends dxf12objects {
/*
  Full OL Crashlock Base    
*/
    public double l1 = 0; // length main
    public double w2 = 0; //
    public double l3 = 0;
    public double w4 = 0;
    public double flange = 0;
    public double btmFlap = 0;    
    public double btm45Rad = 3;
    public double p2ang = 0;  // angled cut-back on the rightside of the flap - SEE allowanceSetup

    public double autoOl = w2; 
    public double autoLugD = 6;
    public double autoLugW = 48;
    public double lugAng = 1;
    public double autoOlLang = 1; // 3.3deg
    public double autoOlRang = 1; // 75deg  
    public double btmRad = 2;

    public double lockSlot = 4;
    public double lockSlotAng = 3;
    
    public String CUT = "CUT";
    public String CREASE = "CREASE";    

    
  protected String drw0701FullOLbtmFalps() {
      // Expects to start at 0,0  
      // Draw 0701 full OL Crashlock Bottom Flaps
      this.drw0701AutoBtmFOL();
      this.drw0701btmFlap45Gl(2);
      this.drw0701AutoBtmSTD();
      this.drw0701btmFlap45Gl(4);
      
    return dxf;     
    } // drw0701FullOLbtmFalps

  
  protected String drw0701FullOLbtmFalpsRC() {
      // Expects to start at 0,0  
      // Draw 0701 full OL Crashlock Bottom Flaps Ready Case Version
      this.drw0701AutoBtmSTD();
      this.drw0701btmFlap45Gl(2);
      this.drw0701AutoBtmFOL();
      this.drw0701btmFlap45Gl(4);
      
    return dxf;     
    } // drw0701FullOLbtmFalpsRC
  
  
  protected void drw0701AutoBtmFOL() {
      // Fully overlapping Auto bottom panel
      double tmpX = this.xabs; // Store Start point top Left of Flap 
      double tmpY = this.yabs;
      double rad1 = this.btmRad;   // LEFT Side (no yet used) - btmRad used as straight both side here
      double rad2 = this.btmRad * 1.25 ; // RIGHT Side
      // this.btmRad - 
      if (this.btmRad == 2) { // E Flute
          rad1 = 4;
      } else if (btmRad >= 3) {
          rad1 = 6;
      } else {
          rad1 = 2;
      }
      
      this.Line(this.btmRad, 0, CUT);
      this.Line(this.autoOlLang, -this.autoOl , CUT);
      
      // Base of Flap & Lock Lug
      double toLug = (this.l1 - (this.btmRad * 2) - this.autoLugW) / 2;
      this.Line(toLug - this.autoOlLang, 0, CUT);
      this.Line(this.lugAng, -this.autoLugD, CUT);
      this.Line(this.autoLugW - (this.lugAng * 2), 0, CUT);
      this.Line(this.lugAng, this.autoLugD, CUT); 
      this.Line(toLug - this.autoOlRang, 0, CUT);
      
      this.relMove(this.btmRad + this.autoOlRang, this.autoOl);
      FilletLines oFl2 = new FilletLines(); // Takes mainly Incremental line values
        oFl2.absFlatXstr = this.xabs; // Start Point
        oFl2.absFlatYstr = this.yabs;   
        oFl2.incLineMeetX = this.btmRad; // WHY - because it was always 3mm fix in allowances
    oFl2.incLineMeetY = 0; 
    oFl2.incAngXend = -this.autoOlRang; 
    oFl2.incAngYend = this.autoOl; 
        oFl2.radius = rad2;
        oFl2.dir = "R"; // LEFT SIDE: X axis = 1
        oFl2.Yaxis = -1; //this.Yaxis; // UP or DOWN
        dxf += oFl2.fillet();       
      
        this.absMove(tmpX, tmpY);
        this.Line(l1, 0, CREASE);
//      this.Line(this.autoOlRang, this.autoOl, CUT);
//      double temp4 = this.l1 - (this.btmRad * 2);
//      this.Line(-temp4, 0, CREASE);
//      this.relMove(temp4, 0);
//      this.Line(this.btmRad, 0, CUT);
    } // drw0701AutoBtmFOL
    
    
  protected void drw0701btmFlap45Gl(int panel) {
      // Glued 45 deg Btm Flap - P2 & P4, 2 forms
      double panelLen = 0; 
      double topAngCutIn = Math.cos(Math.toRadians(45)) * 10; //10mm 45deg cutout section;
      double flangeCutIn = this.flange + 5; // use 5mm on top of flange for now 
      double fishtailRdctn = 1; // Reduction over and above the 2mm? Left edge cut in on straight down line to allow for Fishtailing on the machine
      double leftFlapCutIn = topAngCutIn - fishtailRdctn; // want More than 3mm due to fishtailing so 5mm(2mm reduction from the backward movement)
        double strX = this.xabs;
        double strY = this.yabs;
        
      if (panel == 4) {
        panelLen = this.w4;
      } else {
        panelLen = this.w2;
      }  // end of if
      
      /* Start Left side with 45 deg bend section*/
//      this.Line(this.btm45Rad, 0, CUT);
//      this.Line(topAngCutIn, -topAngCutIn, this.CUT);
      double rad1 = 4; //7; // btmRad: Straight Down Line - E Straight=2, R=4 / 
      double rad2 = 1; //    btm45Rad: E Straight=1 R=1 / B Str=2 R=2.5 / C Str=3 R=4
      // Angled side Radius
      if (this.btm45Rad == 1) { // E and below
         rad2 = 1; 
      } else if (this.btm45Rad == 2) {
         rad2 = 2.5; 
      } else { // C and above
         rad2 = 4; 
      }
      // Straight / 45deg Bend Radius
      if (this.btmRad == 2) { // E Flute
          rad1 = 4;
      } else if (btmRad >= 3) {
          rad1 = 6;
      } else {
          rad1 = 2;
      }
      
        FilletLines oFl = new FilletLines(); // Takes mainly Incremental line values
        oFl.absFlatXstr = this.xabs; // Start Point
        oFl.absFlatYstr = this.yabs;    
        oFl.incLineMeetX = this.btm45Rad; 
        oFl.incLineMeetY = 0; 
        oFl.incAngXend = topAngCutIn; 
        oFl.incAngYend = topAngCutIn; 
        oFl.radius = rad1;
        oFl.dir = "L"; // LEFT SIDE: X axis = 1
        oFl.Yaxis = -1; //this.Yaxis; // UP or DOWN
        dxf += oFl.fillet();
        this.relMove(this.btm45Rad + topAngCutIn, -topAngCutIn);  // "Key" Cut-out     
      
      this.Line(this.btmFlap - topAngCutIn, -this.btmFlap + topAngCutIn, "CUTCRE6");
      this.relMove(-this.btmFlap + topAngCutIn, this.btmFlap - topAngCutIn);
      
      this.Line(-leftFlapCutIn, -leftFlapCutIn, CUT); // Right Angle - Moving away from the 45 deg Cut & Bend intersection     
      this.Line(0, -this.btmFlap + (topAngCutIn + leftFlapCutIn), CUT);

      
      if (panel == 4) {
        this.Line(panelLen - this.btm45Rad - fishtailRdctn - this.p2ang, 0, CUT);   // Flat section at Btm of flap
        this.Line(this.p2ang, this.btmFlap, CUT);
        // Addition for Martin
        this.Line(-flangeCutIn, -flangeCutIn, CUT);
        this.Line(0, -this.btmFlap + flangeCutIn, CUT);
        this.relMove(flangeCutIn, this.btmFlap); 
        //
        this.Line(-panelLen + this.btm45Rad, 0, CREASE);
        this.relMove(panelLen - this.btm45Rad, 0);
      } else {

        this.Line(panelLen - this.btm45Rad - fishtailRdctn - this.p2ang - this.btmRad, 0, CUT); // Base of flap
       // this.relMove(this.btm45Rad, this.btmFlap);
        this.absMove(strX, strY);
        this.Line(panelLen, 0, CREASE);
        
          FilletLines oFl2 = new FilletLines(); // Takes mainly Incremental line values
          oFl2.absFlatXstr = this.xabs; // Start Point
          oFl2.absFlatYstr = this.yabs;   
          oFl2.incLineMeetX = this.btm45Rad; // WHY - because it was always 3mm fix in allowances
      oFl2.incLineMeetY = 0; 
      oFl2.incAngXend = -this.p2ang; 
      oFl2.incAngYend = this.btmFlap; 
          oFl2.radius = rad2;
          oFl2.dir = "R"; // LEFT SIDE: X axis = 1
          oFl2.Yaxis = -1; //this.Yaxis; // UP or DOWN
          dxf += oFl2.fillet();       
        
        this.absMove(strX + panelLen, strY);
      } // end of if
    } // drw0701btmFlap45Gl    
    
  
  protected void drw0701AutoBtmSTD() {
    double angR = Math.tan(Math.toRadians(18)) * this.btmFlap; //  18 / 72 deg (was 15 / 75 deg) 
    double l3HalfCre = (this.l3 - (this.autoLugW + 2)) / 2;
    // Flap outline
//    this.Line(this.btm45Rad, 0, CUT);
//    this.Line(0, -this.btmFlap, CUT);
    double rad1 = btmRad; // Left / FLange side Radius - Very small angle
    double rad2 = this.btm45Rad * 1.333333; // ' 1/3 due to larger angle

    FilletLines oFl = new FilletLines(); // Takes mainly Incremental line values
    oFl.absFlatXstr = this.xabs; // Start Point
    oFl.absFlatYstr = this.yabs;    
    oFl.incLineMeetX = this.btm45Rad; 
  oFl.incLineMeetY = 0; 
  oFl.incAngXend = 0; 
  oFl.incAngYend = this.btmFlap; 
    oFl.radius = rad1;
    oFl.dir = "L"; // LEFT SIDE: X axis = 1
    oFl.Yaxis = -1; //this.Yaxis; // UP or DOWN
    dxf += oFl.fillet();
    this.relMove(this.btm45Rad, -this.btmFlap);
    
    this.Line(this.l3 - (this.btm45Rad * 2) - angR, 0, CUT); // Btm Line of Flap
    
//    this.Line(this.btm45Rad, 0, CUT);
//    this.Line(angR, this.btmFlap, CUT);
    this.relMove(angR + this.btm45Rad, this.btmFlap);
    FilletLines oFl2 = new FilletLines(); // Takes mainly Incremental line values
    oFl2.absFlatXstr = this.xabs; // Start Point
    oFl2.absFlatYstr = this.yabs;   
    oFl2.incLineMeetX = this.btm45Rad; 
  oFl2.incLineMeetY = 0; 
  oFl2.incAngXend = -angR; 
  oFl2.incAngYend = this.btmFlap; 
    oFl2.radius = rad2;
    oFl2.dir = "R"; // LEFT SIDE: X axis = 1
    oFl2.Yaxis = -1; //this.Yaxis; // UP or DOWN
    dxf += oFl2.fillet();
//    this.relMove(angR + this.btm45Rad, this.btmFlap);
    
    // Base bend and lock slot
    this.Line(-l3HalfCre, 0, CREASE);
    this.Line(0, this.lockSlot, CUT);
    this.Line(-(this.autoLugW + 2), 0, CUT);
    this.Line(0, -this.lockSlot, CUT);
    this.Line((this.autoLugW + 2) / 2, -this.lockSlotAng, CUT);
    this.Line((this.autoLugW + 2) / 2, this.lockSlotAng, CUT);
    this.relMove(-(this.autoLugW + 2), 0);
    this.Line(-l3HalfCre, 0, CREASE);
    
    this.relMove(this.l3, 0);
    
  } // drw0701AutoBtmSTD

  
  
    
  
} // Class
