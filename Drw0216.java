package auto;

public class Drw0216 extends dxf12objects {

    public double l1 = 0; // length main
    public double w2 = 0; //
    public double l3 = 0;
    public double w4 = 0;
    public double flange = 0;
    public double btmFlap = 0;
    public double tab = 0;
    public double slot = 2;

    public String CUT = "CUT";
    public String CREASE = "CREASE";      

    
    protected String drw00216btmFalps() {
      // Expects to start at 0,0  
      // Draw 0216 Pushlock / Envelope base Bottom Flaps
    double crn = l3 / 4;  
    double flplen = l3 / 2;
    
    femalepush(l1, crn); // (0,0), (l1,0), TRUE, bflap,2,(l3/4),30);
    inrside(w2, crn, flplen); // (0,0), (w2,0), TRUE, bflap,2,(l3/4),(p3/2));
    malepush(l3, crn); // (0,0), (l3,0), TRUE, bflap,(l3/4),30);
    this.relMove(w4, 0);
    inrside(w2, crn, flplen); // (0,0), (-w4, 0), FALSE, bflap,2,(l3/4),(l3/2));
      
    return dxf;     
  } // drw00216btmFalps   
    
  // *** Draw a lock MALE flap ***
    protected void malepush(double dis, double crn) { //(CONST vector start, endpnt_Xaxis, CONST Boolean YAxisUpwards,CONST LENGTH flp,crn,tab)
    //btmFlap-1/2 flap
    //crn-Quarter cut in
    //tab-lock tab usually 30
    
      this.relMove(dis, 0);
      this.Line(-dis, 0, CREASE);

      this.Line(crn, -btmFlap, CUT);
      this.Line(0, -tab, CUT);
      this.Line(dis-(crn*2), 0, CUT);
      this.Line(0, tab, CUT);
      this.Line(crn, btmFlap, CUT);
    } // malepush


    // *** Draw a lock FEMALE flap ***
    protected void femalepush(double dis, double crn) { //(CONST vector start, endpnt_Xaxis, CONST Boolean YAxisUpwards,CONST LENGTH btmFlap,slot,crn,tab)
    //btmFlap-1/2 flap
    //slot-cut in 
    //crn-Quarter cut in
    //tab-lock tab usually 30

      this.relMove(dis-slot,0);
      this.Line( -dis+(slot*2) , 0, CREASE);

      this.relMove(-slot, 0);
      this.Line(slot , 0, CUT);
      this.Line(0, -btmFlap-tab, CUT);
      this.Line(crn-slot, 0, CUT);
      this.Line(0, tab, CUT);
      this.Line(dis-crn*2 , 0, CUT);
      this.Line(0, -tab, CUT);
      this.Line(crn-slot, 0, CUT);
      this.Line(0, btmFlap+tab, CUT);
      this.Line(slot , 0, CUT);
    } // femalepush


    // *** Draw a Inner Side flap ***
    protected void inrside(double dis, double crn, double flplen) { //(CONST vector start, endpnt_Xaxis, CONST Boolean YAxisUpwards,CONST LENGTH btmFlap,slot,crn,btmFlaplen)
    //btmFlap-1/2 flap
    //slot-cut in 
    //crn-Quarter cut in
    //flap length (do size calcs in main macro)

      this.relMove(dis-slot, 0);
      this.Line(-dis+slot , 0, CREASE);

      this.Line(dis-btmFlap, -crn, CUT);
      this.Line(0, -flplen+crn, CUT);
      this.Line(btmFlap-slot, 0, CUT);
      this.Line(0, flplen, CUT);
      this.Line(slot , 0, CUT);
    } // inrside
    
    
} // Drw0216
