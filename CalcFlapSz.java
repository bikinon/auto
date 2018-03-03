package auto;

public class CalcFlapSz {
  // Passed values
    public double length = 0; //l - I/D L
    public double width = 0; //w - I/D W
    public double flapEx = 0; // Flap overlap or Gap, etc
    public String flapstyle = ""; //flapstyle - 0200 etc
    public String flute = ""; //flute - INT for flute 0=E 1=B 2&3=C 4=BC
    public boolean OffsetY = true; //OffsetY - Boolean TRUE=Yes

// *** Internal Values ********************************
    private double fa = 0;  // flap allowance
    private double fr = 0;  // flap rounding figure
    
// *** Returned Values: ***********************************
    // General Sizes
    public double otrFlap = 0; //  Outer flap size
    public double inrFlap = 0; // Inner flap size
    public double bofst = 0; // 020x Bend Offset
    public double da = 0;    //da - part depth allowance
    // *** Top Flaps ***
    // 0215PS / 0215
    public double t0215TuckFlap = 0;
      // FullOverlapPS & HaammerHeadOL 
    public double topOtrRad = 3;
    public double topInrRad = 2; 
    // *** Btm Flaps ***
    // Auto Full OL flap
    public double autoOl = 0; // width + 0.5; 
    public double autoLugD = 6;
    public double lugAng = 1;
    public double autoOlLang = 0;
    public double autoOlRang = 0;
    public double autoLugW = 0;
    public double p2ang = 0;
    public double p3ang = 0;
    public double btmFlap = 0; // Used on Btm panels 2, 3 & 4
    
    
    public void flapsize(double w2, double l3) { // double l,w,da,bof, 
    //*****************************
    //calc flap sizes 01/01/98 - TG
    // This version JAVA 04/05/2017 - TG  
    //*****************************
      switch (flute) { // Set standard allowances
      case "E": // E flute   
        da=1;
        fa=1; 
        fr=1; 
        bofst=1;
        break;
      case "B": // B flute
        da=1.5;
        fa=1; 
        fr=1; 
        bofst=2;
        break;
      case "C": 
      case "EB": // C flute
        da=2.5;
        fa=3; 
        fr=-1; 
        bofst=3;
        break;
      case "BC": // BC flute
        da=3.5;
        fa=5; 
        fr=-1; 
        bofst=5;
        break;
      } // switch (flute)

      if (OffsetY == false) {
        bofst=0;
      } //if (OffsetY
      
      
    // {*** Style Allowances ***}
     switch (flapstyle) {
     case "0200 (Raw Edge)":
       style0200();
       break;
     case "0201 (OFOTB)":
       style0201();
       break;
     case "0202 (OFO)":
       style0202();
       break;
     case "0203 (OFFO)":
       style0203();
       break;
     case "0204 (AFM)":
       style0204();
       break;
     case "0204-01 (Canners)":
       break;
     case "0205 (IFM)":
       style0205();
       break;
     case "0206":
       style0206();
       break;
     case "0207-00":
       break;
     case "0209 (Short Top Flap)":
       style0209f();
       break;
     case "0209 (Gap Flaps)":
       style0209g();
       break;
     case "0210":
       break;
     case "0216":
       break;
     case "0217":
       break;
     case "0225":
       break;
     case "0711":
       break;
     case "Full Overlap P&S / Rippa": // Full Overlap with PS
       styleFullOverlapPS(w2);
       break;
     case "Hammer Head OL":
       this.styleHammerHeadOL(w2);
       break;
     case "0215+ P&S / Rippa":   
       this.style0215PS(w2);
       break;
     case "0701 Auto Full OL": 
     case "0701 Auto Full OL RC":     
       this.style0701AutoFullOL(w2, l3);
       break;
     case "0700 Auto Std":
       this.style0700AutoStd(w2);
       break;
    case "0216 (Envelope Base)":
       this.style0216();
       break;   
     } // switch flapstyle
     
  } // *** Style Allowances ***

    
    protected void style0200() {
       da = 0;
       otrFlap = 0;
       bofst=0;       
    } // style0200
      
    
    protected void style0201() {
//          topFlap = (w2) / 2 + 1;
//          topOtrRad = 3;
//          topInrRad = 2; 
//        bofst=2;
      otrFlap=(width/2)+fa;                          
      if (width/2 != Math.rint(width/2)) {                      
        otrFlap = ((width+fr)/2)+fa;
        }
     inrFlap=otrFlap+bofst; // Inner flap size      
    } // style0201

    
    protected void style0202() {
      // GetInput("0202-00 Outer Flap Overlap", flapEx);
  //      bofst=2;
        otrFlap=((width + flapEx)/2)+fa;                          
      if ((width + flapEx) / 2 != Math.rint((width + flapEx) / 2) ) {                       
        otrFlap=(((width + flapEx) + fr) / 2) + fa;
      } 
      inrFlap = otrFlap + bofst; // Inner flap size
      
    } // style0202

    
    protected void style0203() {
      switch (flute) {             
        case "E":  // E flute   
          da = 1;
          otrFlap = width;
          bofst=2; 
          break;
        case "B":  // B flute
          da = 1.5;
          otrFlap = width;
          bofst=3;
          break;
        case "C":
        case "EB":  // C flute
          da = 3;
          otrFlap = width;
          bofst=4;
          break;
        case "BC":  // BC flute
          da = 6;
          otrFlap = width+2;
          bofst=6;
          break;
        } // switch flute
       if (OffsetY==false) {
        bofst=0;
       }
      inrFlap=otrFlap+bofst; // Inner flap size
      
    } // style0203

    
    protected void style0204() {
    // bofst=2;
     otrFlap=(width/2)+fa;                          
     if (width/2 != Math.rint(width/2)) {                       
       otrFlap=((width+fr)/2)+fa;
     }  
     inrFlap=(length/2)+fa;                          
     if (length/2 != Math.rint(length/2)) {                     
       inrFlap=((length+fr)/2)+fa;
     }
      
    } // style0204  
    
    
    protected void style0204c() {
  //   bofst=2;
     otrFlap=(width/2)+fa;                          
     if (width/2 != Math.rint(width/2)) {                       
       otrFlap=((width+fr)/2)+fa;
     } 
     // GetInput("0204-01 (R) Inner flap increase over Outer flap", flapEx);
     inrFlap=otrFlap+flapEx;      
    } // style0204c
    
    
    protected void style0205() {
    //  bofst=2;
    otrFlap=(length/2)+fa;                          
    if (length/2 != Math.rint(length/2)) {                      
      otrFlap=((length+fr)/2)+fa;
    }
    inrFlap=otrFlap+bofst; // Inner flap size
      
    } // style0205  
    
    
    protected void style0206() {
      
    } // style0206
    
    
    protected void style0207() {    
    //  bofst=2;
      inrFlap=width; // just a dummy fig for biflap
    } // style0207
    
    
    protected void style0209g() {
    //flapEx=6;
    // GetInput("0209-02 Outer Flaps Gap", flapEx);
   //   bofst=2;
      otrFlap=((width-flapEx)/2)+fa;                          
    if ((width-flapEx)/2 != Math.rint(width-flapEx) / 2 ) {                       
      otrFlap=(((width-flapEx)+fr)/2)+fa;
    }
    inrFlap=otrFlap+bofst; // Inner flap size
      
    } // style0209g
    
    
    protected void style0209f() {
    //  bofst=2;
      otrFlap=this.flapEx;
    // GetInput("0209-01: Short Flap (65mm std size)", otrFlap);
      if (flute == "EB") {
        otrFlap = otrFlap+2;
      }
    inrFlap=otrFlap+bofst; // Inner flap size
      
    } // style0209f
    
    
    protected void style0210() {
    //  bofst=2;
      inrFlap=(length/2)+fa;                          
      if (length/2 != Math.rint(length/2)) {                        
        inrFlap=((length+fr)/2)+fa;
      }
      
    } // style0210
    
    
    protected void style0216() {
      otrFlap=(width/2)+fa;                          
      if (width/2 != Math.rint(width/2)) {                      
        otrFlap=((width+fr)/2)+fa;
        } 
      inrFlap=(length/2)+fa;                          
      if (length/2 != Math.rint(length/2)) {                      
        inrFlap=((length+fr)/2)+fa;
      }
      
    } // style0216
    
    
    protected void style0217() {
      otrFlap=(width/2)+fa;                          
      if (width/2 != Math.rint(width/2)) {                      
        otrFlap=((width+fr)/2)+fa;
      }
      inrFlap=55;     
    } // style0217
    
    
    protected void style0225() {
      inrFlap=width+fa;  
    } // style0225
    
    
    protected void style0711() {
    otrFlap=(width/2)+fa;                          
    if (width/2 != Math.rint(width/2)) {                      
      otrFlap=((width+fr)/2)+fa;
    }
    inrFlap=otrFlap; // Inner flap size
    
    } // style0711
    

    protected void styleFullOverlapPS(double w2) {
        otrFlap = w2;
        topOtrRad = 3;
        topInrRad = 2;    
    } // style0225
    
    
    protected void styleHammerHeadOL(double w2) {
        double hammerOL = 38; // Lil use 25mm
        otrFlap = (w2 + hammerOL) / 2;
        topOtrRad = 3;
        topInrRad = 2;   
    } // styleHammerHeadOL
    
    
    protected void style0215PS(double w2) {
      switch (flute) { // Set standard allowances
      case "E": // E flute   
            t0215TuckFlap = w2 + 1.5;
        break;
      case "B": // B flute
            t0215TuckFlap = w2 + 2;
        break;
      case "C": 
      case "EB": // C flute
          t0215TuckFlap = w2 + 3;
        break;
      case "BC": // BC flute
         t0215TuckFlap = w2 + 4;
        break;
      } // switch

    }  // style0215PS
   
    
    protected void style0701AutoFullOL(double w2, double l3) {
        //  FOL Base angles
      btmFlap = w2 / 2; // Used on Btm panels 2, 3 & 4
        lugAng = 1;
      switch (flute) { // Set standard allowances
      case "E": // E flute   
          autoOl = width + 1; 
            autoLugD = 4.5;
        break;
      case "B": // B flute
          autoOl = width + 1.5; 
            autoLugD = 4.5;
        break;
      case "C": 
      case "EB": // C flute
          autoOl = width + 2.5; 
            autoLugD = 5;
        break;
      case "BC": // BC flute
          autoOl = width + 3; 
            autoLugD = 6;
        break;
      } // switch 
        
        autoOlLang = autoOl * Math.tan(Math.toRadians(3.3)); // 3.3deg
        autoOlRang = autoOl * Math.tan(Math.toRadians(18)); //  18 / 72 deg (was 15 / 75 deg) 
        
        autoLugW = (int)(l3 / 6) + 0.5;
        if (autoLugW < 25) {  // Min & Max for base lock lug
          autoLugW = 25;
        } // end of if
        if (autoLugW > 75) {
          autoLugW = 75;
        } // end of if
        
        p2ang = btmFlap * Math.tan(Math.toRadians(20)); // 20 / 70 deg
        p3ang = btmFlap * Math.tan(Math.toRadians(18)); //  18 / 72 deg (was 15 / 75 deg)  
    } // style0701AutoFullOL

    
    protected void style0700AutoStd(double w2) {
      btmFlap = w2 / 2; // Used on Btm panels 2, 3 & 4
      otrFlap = btmFlap;
        p2ang = btmFlap * Math.tan(Math.toRadians(4)); // 20 / 70 deg
        p3ang = btmFlap * Math.tan(Math.toRadians(6)); //  18 / 72 deg (was 15 / 75 deg) 
      
    } // style0700AutoStd
    
    
} // class
