/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sniffdog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import org.jnetpcap.Pcap;
import org.jnetpcap.packet.JFlow;
import org.jnetpcap.packet.JFlowKey;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JScanner;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;

/**
 *
 * @author Rishabh
 */
public class FlowDiagram extends javax.swing.JFrame {

    /**
     * Creates new form FlowDiagram
     */
    public FlowDiagram() {
        initComponents();
        settablemodel();
        extract_info();
        process_info();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel2.setLayout(null);

        jLabel1.setText("Total packets");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(10, 20, 80, 20);

        jLabel2.setText("Total Streams");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(420, 20, 80, 20);

        jTextField2.setEditable(false);
        jPanel2.add(jTextField2);
        jTextField2.setBounds(100, 10, 100, 30);

        jTextField3.setEditable(false);
        jPanel2.add(jTextField3);
        jTextField3.setBounds(510, 10, 90, 30);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(30, 160, 570, 260);

        jLabel5.setText("Stream");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(160, 60, 50, 20);
        jPanel2.add(jTextField5);
        jTextField5.setBounds(220, 50, 100, 30);

        jButton2.setText("Fetch");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);
        jButton2.setBounds(340, 50, 73, 23);

        jLabel6.setText("Host ");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(100, 100, 50, 14);

        jLabel7.setText("Destination");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(100, 130, 54, 14);

        jTextField6.setEditable(false);
        jPanel2.add(jTextField6);
        jTextField6.setBounds(170, 90, 130, 30);

        jTextField7.setEditable(false);
        jPanel2.add(jTextField7);
        jTextField7.setBounds(170, 120, 130, 30);

        jLabel8.setText("Total Packets");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(380, 100, 80, 14);

        jTextField8.setEditable(false);
        jPanel2.add(jTextField8);
        jTextField8.setBounds(480, 90, 80, 30);

        jTextField9.setEditable(false);
        jPanel2.add(jTextField9);
        jTextField9.setBounds(480, 120, 80, 30);

        jLabel9.setText("Total time");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(380, 130, 70, 14);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(40, 20, 640, 432);

        pack();
    }// </editor-fold>//GEN-END:initComponents
UI_Processing up;

    private void settablemodel() {
        DefaultTableModel dtm = new DefaultTableModel(new String[]{"PacketNo", "Time", "Source port", "Direction", "Destport", "Flags"}, 0);
        jTable2.setModel(dtm);
        up = new UI_Processing(dtm);
    }

    private void extract_info() {
        String addr = "C:\\Users\\Rishabh\\Downloads\\NetworkAnal\\" + User_Interface.filename + ".pcap";

        Pcap pcap
                = Pcap.openOffline(addr, new StringBuilder());

        PcapPacketHandler<String> jpacketHandler = new handler_protocol();
        JScanner.getThreadLocal().setFrameNumber(0);
        pcap.loop(-1, jpacketHandler, "jNetPcap rocks!");

        pcap.close();

    }

    private class handler_protocol implements PcapPacketHandler<String> {

        int num = 0;

        @Override
        public void nextPacket(PcapPacket packet, String user) {
            JFlowKey jfkey = packet.getState().getFlowKey();
            totalpackets++;
            JFlow jf = map.get(jfkey);
            if (jf == null) {
                numkey.put(num, jfkey);
                num++;
                map.put(jfkey, jf = new JFlow(jfkey));
                //estbs.add(-1);
            }
            jf.add(packet);

        }

    }
    Map<JFlowKey, JFlow> map = new HashMap<JFlowKey, JFlow>();
    HashMap<Integer, JFlowKey> numkey = new HashMap<>();

    static int totalpackets = 0;
//static JFlowMap map=new JFlowMap();

    private void process_info() {
//totalpackets=map.getTotalPacketCount();
        jTextField2.setText(totalpackets + "");
        jTextField3.setText(map.keySet().size() + "");

    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        up.cleartable();
        int x = Integer.parseInt(jTextField5.getText());
        if (x < 1 && x > totalpackets) {
        } else {

            JFlow flow = map.get(numkey.get(x));
            List<JPacket> packets = flow.getAll();
            int i = 1;
            String source = "";
            String dest = "";

            int sourceport = 0, destport = 0;
            
            Ip4 ip = new Ip4();
            Tcp tcp = new Tcp();
            for (JPacket jp : packets) {
               String comments = "";
                Date date=new Date(jp.getCaptureHeader().timestampInMillis());
                        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss:SSS");
                if (i == 1) {
                    if (jp.hasHeader(ip)) {
                        source = FormatUtils.ip(ip.source());
                        dest = FormatUtils.ip(ip.destination());
                        jTextField6.setText(source);
                        jTextField7.setText(dest);
                    }
                    if (jp.hasHeader(tcp)) {
                        sourceport = tcp.source();
                        destport = tcp.destination();
                        if (tcp.flags_ACK()) {
                            comments += "ACK ";
                        }
                        if (tcp.flags_SYN()) {
                            comments += "SYN ";
                        }
                        if (tcp.flags_FIN()) {
                            comments += "FIN ";
                        }
                    }
                    up.addRow(new String[]{i + "",sdf.format(date)+"" ,sourceport + "", "------>", destport + "", comments});
                } else {
                    String direc = "";
                    String sourceip="";
                    if (jp.hasHeader(ip)) {
                        sourceip = FormatUtils.ip(ip.source());
                        if (sourceip.equals(source)) {
                            direc = "------>";
                        } else {
                            direc = "<------";
                        }

                    }
                    if (jp.hasHeader(tcp)) {
                       sourceport = tcp.source();
                        destport = tcp.destination();
                        if(sourceip.equals(dest)){
                        int temp=sourceport;sourceport=destport;destport=temp;
                        }
                        if (tcp.flags_ACK()) {
                            comments += "ACK ";
                        }
                        if (tcp.flags_SYN()) {
                            comments += "SYN ";
                        }
                        if (tcp.flags_FIN()) {
                            comments += "FIN ";
                        }
                    }
                    up.addRow(new String[]{i + "", sdf.format(date)+"",sourceport + "", direc, destport + "", comments});
                }

                i++;
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FlowDiagram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FlowDiagram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FlowDiagram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FlowDiagram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FlowDiagram().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    protected static javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    protected static javax.swing.JTable jTable2;
    protected static javax.swing.JTextField jTextField2;
    public static javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    public static javax.swing.JTextField jTextField6;
    public static javax.swing.JTextField jTextField7;
    public static javax.swing.JTextField jTextField8;
    public static javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
