package auto;

import javax.swing.JOptionPane;

public class MachineSz {

    // Machine Size
    public String diecutter = "Elite";
    public String desStyle = "";
    public double s1wayNet = 0;
    public double n2wayNet = 0;
    public double s1wayGross = 0;
    public double n2wayGross = 0;
    public boolean stdFluteFeed = true;
    public double l1 = 0; // length panel 1 main
    public double w2 = 0; //
    public double l3 = 0;
    public double w4 = 0;
    public double flange = 0;   
    public double topFlap = 0;
    public double btmFlap = 0;
    public double pnlDepth = 0; // Panel Depth
    
public boolean eliteOK() {
    boolean rtnVal = true;
    
    String msg = "";
    double trim1s = 35;
    double trim2n = 30; 
    double maxFeed = 1200 - trim1s; // change from 1100
    double maxnCrsFeed = 1620 - trim2n;

    if(s1wayNet < 152) {
      msg = msg + "ELITE - 1st WAY TOO SMALL by " + Double.toString(s1wayNet - 152) + "\n";
    }
     if(n2wayNet < 600) {
      msg = msg + "ELITE - 2nd WAY TOO SMALL by " + Double.toString(n2wayNet - 600) + "\n";
    }   
    
    JOptionPane.showMessageDialog(null, msg, "Plant Limits", JOptionPane.WARNING_MESSAGE);  
    return rtnVal;
} // eliteOK


public boolean topraOK() {
    boolean rtnVal = true;
    
    String msg = "";
    double trim1s = 30;
    double trim2n = 30; 
    double maxFeed = 1200 - trim1s; // change from 1100
    double maxnCrsFeed = 2200 - trim2n;

    if(s1wayNet > maxFeed) {
      msg = msg + "TOPRA - 1st WAY TOO BIG by " + Double.toString(s1wayNet - maxFeed) + "\n";
    }
     if(n2wayNet > maxnCrsFeed) {
      msg = msg + "TOPRA - 2nd WAY TOO BIG by " + Double.toString(n2wayNet - 600) + "\n";
    }   
    
    JOptionPane.showMessageDialog(null, msg, "Plant Limits", JOptionPane.WARNING_MESSAGE);
    return rtnVal;
} // topraOK


public boolean bobstVgOK() {
    boolean rtnVal = true;
    
    String msg = "";
    double trim1s = 30;
    double trim2n = 30; 
    double maxFeed = 1200 - trim1s; // change from 1100
    double maxnCrsFeed = 2200 - trim2n;

    if(s1wayNet > maxFeed) {
      msg = msg + "BOBST VG - THROUGH MC SIZE TOO BIG by " + Double.toString(s1wayNet - maxFeed) + "\n";
    }
     if(n2wayNet > maxnCrsFeed) {
      msg = msg + "BOBST VG - CROSS MC SIZE TOO BIG by " + Double.toString(n2wayNet - 600) + "\n";
    }   
    if(s1wayNet > maxFeed) {
      msg = msg + "BOBST VG - THROUGH MC SIZE TOO BIG by " + Double.toString(s1wayNet - maxFeed) + "\n";
    }
     if(n2wayNet > maxnCrsFeed) {
      msg = msg + "BOBST VG - CROSS MC SIZE TOO BIG by " + Double.toString(n2wayNet - 600) + "\n";
    }
     
    if(l1 > 10) {
      msg = msg + "BOBST VG - Length(P1) TOO BIG by " + Double.toString(l1 - 10) + "\n";
    }
     if(l1 < 10) {
      msg = msg + "BOBST VG - Length(P1) TOO SMALL by " + Double.toString(l1 - 10) + "\n";
    }
    if(l1 + w2 > 10) {
      msg = msg + "BOBST VG - P2+P3 TOO BIG by " + Double.toString(l1 - 10) + "\n";
    }
     if(l1 + w2 < 10) {
      msg = msg + "BOBST VG - P2+P3 TOO SMALL by " + Double.toString(l1 - 10) + "\n";
    }     
 
    if(flange > 30) {
      msg = msg + "BOBST VG - Depth TOO BIG by " + Double.toString(flange - 30) + "\n";
    }
     if(flange < 6) {
      msg = msg + "BOBST VG - Depth TOO SMALL by " + Double.toString(flange - 6) + "\n";
    }
     
    if(pnlDepth > 10) {
      msg = msg + "BOBST VG - Depth TOO BIG by " + Double.toString(pnlDepth - 10) + "\n";
    }
     if(pnlDepth < 10) {
      msg = msg + "BOBST VG - Depth TOO SMALL by " + Double.toString(pnlDepth - 10) + "\n";
    }
     
     JOptionPane.showMessageDialog(null, msg, "Plant Limits", JOptionPane.WARNING_MESSAGE);
     return rtnVal;
} // bobstVgOK


public boolean pakmet() {
//-*********************
// Pakmet gluer
// tg - 11/7/95
//-*********************
boolean rtnVal = true;
  String msg = "";

    if(s1wayNet < 152) {
      msg = msg + "Check LIMITS - PAKMET 1st WAY TOO SMALL by " + Double.toString(s1wayNet - 152) + "\n";
    }
/*    
    men := 'Check LIMITS - PAKMET' + #13#10 + '1st WAY TOO BIG by ' + IntToStr(s1brd - 2540);
    If s1brd > 2540 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    men := 'Check LIMITS - PAKMET' + #13#10 + '2nd WAY TOO SMALL by ' + IntToStr(n2brd - 322);
    If n2brd < 322 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    men := 'Check LIMITS - PAKMET' + #13#10 + '2nd WAY TOO BIG by ' + IntToStr(s1brd - 2544);
    If n2brd > 2544 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    Val(spec.EditLENGTH.Text, temp, Crap);
    men := 'Check LIMITS - PAKMET' + #13#10 + 'LENGTH TOO SMALL by ' + IntToStr(temp - 76);
    If temp < 76 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    Val(spec.EditLENGTH.Text, temp, Crap);
    men := 'Check LIMITS - PAKMET' + #13#10 + 'LENGTH TOO BIG by ' + IntToStr(Temp - 615);
    If Temp > 615 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    {Val(spec.EditWIDTH.Text, temp, Crap);
    men := 'Check LIMITS - PAKMET' + #13#10 + 'CHECK FLAP SIZE? TOO BIG by ' + IntToStr((temp / 2 - 355)) + '?';
    If (temp / 2) > 355 Then
      MessageDlg(men, mtInformation, [mbOk], 0);}

    men := 'Check LIMITS - PAKMET' + #13#10 + 'WIDTH TOO SMALL by ' + IntToStr(temp - 76);
    If temp < 76 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    men := 'Check LIMITS - PAKMET' + #13#10 + 'WIDTH TOO BIG by ' + IntToStr(temp - 615);
    If temp > 615 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    Val(spec.EditDEPTH.Text, temp, Crap);
    men := 'Check LIMITS - PAKMET' + #13#10 + 'DEPTH TOO SMALL by ' + IntToStr(temp - 50);
    If temp < 50 Then
      MessageDlg(men, mtInformation, [mbOk], 0);

    men := 'Check LIMITS - PAKMET' + #13#10 + 'DEPTH TOO BIG by ' + IntToStr(temp - 1828);
    If temp > 1828 Then
      MessageDlg(men, mtInformation, [mbOk], 0);
*/
  JOptionPane.showMessageDialog(null, msg, "Plant Limits", JOptionPane.WARNING_MESSAGE);
  return rtnVal;
} // pakmet


    
} // MachineSz
