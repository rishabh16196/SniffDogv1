/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sniffdog;

import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import org.jnetpcap.Pcap;
import org.jnetpcap.packet.PcapPacket;

/**
 *
 * @author Rishabh
 */
public class UI_Processing {
    DefaultTableModel dtm;
    JTextArea packet_info;
    public UI_Processing(DefaultTableModel dtm,JTextArea packet_info) {
        this.dtm = dtm;
        this.packet_info=packet_info;
    }
    public UI_Processing(DefaultTableModel dtm){
    this.dtm=dtm;
    }
    public void addRow(Object[] rowData){
    dtm.addRow(rowData);
    }
    public void cleartable(){
    dtm.setRowCount(0);}
    public void display_info(int i){
    packet_info.setText(Pcap_File_Processing.p[i].toString());
    packet_info.setCaretPosition(0);
    }
}
