package auto;

public class Drw0215ps extends dxf12objects {

  public double l1 = 0; // length main
  public double w2 = 0; //
  public double l3 = 0;
  public double w4 = 0;
  public double flange = 0; 

  public double dotr = 4;
  // 0215PS
  public double t0215sInr = 1; // Flap Side Inner
  public double t0215sOtr = 2;
  public double t0215BndOfst = 2;
  public double t0215DustInSet = 2;
  public double t0215TuckFlap = this.w2 + 1.5;
  public double tuckTabPs = 65;   
  
  public String CUT = "CUT";
  public String CREASE = "CREASE";  
    
  
  public String drw0215ps() {
    this.absMove(0, this.dotr);
    d0215TuckPs();
    relMove(this.l1, 0);
    d0215DustInrFlp(2);
    Line( this.l3, 0, CUT);
    relMove(this.w4, 0);
    this.Xaxis = -1;
    d0215DustInrFlp(4);
    
    return dxf;
    
  } // Drw0215ps  
  
  
  protected void d0215DustInrFlp(int panel) {
   // Start at the Cut Edge Enterance   
    double panelLen = this.w4;
    double totFlap = t0215TuckFlap + tuckTabPs;   
    
    if (panel == 2) {
      panelLen = this.w2;
     // Line(-rightInset, 0, CUT); // We want an Inset on panel 2 
    } else {
     // rightInset = 0; // Not required for Panel 4
    } // if
    
    if (totFlap > (this.l3 / 2)) {
      totFlap = (this.l3 / 2); 
    } // if
    
    relMove(panelLen, 0);
    Line( -t0215DustInSet, 0, CUT);
    Line( 0, totFlap, CUT);
    if (panel == 2) {
      Line( -(panelLen - t0215DustInSet - t0215sOtr), 0, CUT);
      relMove( -t0215sOtr, -totFlap);
      Line( panelLen - t0215DustInSet, 0, CREASE);
      relMove( t0215DustInSet, 0);
    } else {
      Line( -(panelLen - t0215DustInSet), 0, CUT);
      Line( 0, -totFlap, CUT);
      Line( panelLen - t0215DustInSet, 0, CREASE);
    }
  } // d0215DustInrFlp
  
  
  protected void d0215TuckPs_org() {
    // Tuck top with Peel & Seal for Outside seal
    Line(-t0215sOtr, t0215BndOfst, CUT);
    Line( 0, t0215TuckFlap, CUT);
    
    Line( t0215sInr + t0215sOtr, 0, CUT);// inset
    
    Line( this.l1 - (t0215sInr * 2), 0, CREASE);
    relMove(-(this.l1 - (t0215sInr * 2)), 0);
    
    Line( 0, tuckTabPs, CUT);
    
    Line( this.l1 - (t0215sInr * 2), 0, CUT); // Top Edge
    
    Line( 0, -tuckTabPs, CUT);
    Line( t0215sInr + t0215sOtr, 0, CUT);// inset
    Line( 0, -t0215TuckFlap, CUT);
    Line(-t0215sOtr, -t0215BndOfst, CUT);
    this.relMove(t0215sOtr, t0215BndOfst);
    Line(-(this.l1 + (t0215sOtr * 2)), 0, CREASE);
    this.relMove(t0215sOtr, -t0215BndOfst);
    
  }// d0215TuckPs
  
  
    protected void d0215TuckPs() {
    // Tuck top with Peel & Seal for Outside seal
   t0215sOtr = 0;
   t0215BndOfst = 0;
   t0215sInr = 4;
   tuckTabPs = 55;
   double tPsBtm = 12;
   double tabVert = 16;
   double tabLen = 15;
   double tabBtmAng = 3;
   double tabTopAng = 2;
   double tabInsH = 5;
   double tabInsV = 5;
   
    // Line(-t0215sOtr, t0215BndOfst, CUT);
    Line( 0, t0215TuckFlap, CUT);
    Line( t0215sInr + t0215sOtr, 0, CUT);// inset
    
    Line( this.l1 - (t0215sInr * 2), 0, CREASE);
    relMove(-(this.l1 - (t0215sInr * 2)), 0);   
    
    Line( 0, tPsBtm, CUT);
    
    Line( tabLen, tabBtmAng, CUT);
    relMove(-tabLen, -tabBtmAng);
    
    Line( 0, tabVert, CUT);    
    Line( tabInsH, tabInsV, CUT);
    
    Line( tabLen, -tabTopAng, CUT);
    relMove(-tabLen, tabTopAng);
    
    Line( 0, tuckTabPs - (tPsBtm + tabVert + tabInsV), CUT);
    
    Line( this.l1 - (t0215sInr * 2) - (tabInsH * 2), 0, CUT); // Top Edge
    
    //Line( 0, -tuckTabPs, CUT);
    Line( 0, -tuckTabPs + (tPsBtm + tabVert + tabInsV), CUT);
    
    Line( -tabLen, -tabTopAng, CUT);
    relMove(tabLen, tabTopAng);
    
    Line( tabInsH, -tabInsV, CUT);    
    Line( 0, -tabVert, CUT);

    Line( -tabLen, tabBtmAng, CUT);
    relMove(tabLen, -tabBtmAng);

    Line( 0, -tPsBtm, CUT);
    
    Line( t0215sInr + t0215sOtr, 0, CUT);// inset
    Line( 0, -t0215TuckFlap, CUT);
    Line(-t0215sOtr, -t0215BndOfst, CUT);
    this.relMove(t0215sOtr, t0215BndOfst);
    Line(-(this.l1 + (t0215sOtr * 2)), 0, CREASE);
    this.relMove(t0215sOtr, -t0215BndOfst);
    
  }// d0215TuckPs
  
} // Drw0215ps
