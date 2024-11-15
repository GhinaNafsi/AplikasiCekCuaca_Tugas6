
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class CekCuacaFrame extends javax.swing.JFrame {
 private static final String API_KEY = "f2c4e46e7aba3744d56524fd1896414d"; // Hanya kunci API saja
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String CITY_FILE_PATH = "data/cities.txt";
    private static final String WEATHER_FILE_PATH = "data/weather_data.csv";
    private DefaultTableModel tableModel;
    
      // Fungsi untuk mengambil data cuaca dari API
    public String DapatDataCuaca(String cityName) {
        String response = "";
        try {
            // Buat URL lengkap dengan memasukkan cityName, API_KEY, dan bahasa (lang=id)
            String urlString = BASE_URL + "?q=" + cityName + "&appid=" + API_KEY + "&units=metric&lang=id";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            response = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    public void MenampilkanCuaca(String cityName) throws JSONException {
        // Dapatkan data cuaca dalam bentuk JSON
        String jsonData = DapatDataCuaca(cityName);
        JSONObject jsonObjek = new JSONObject(jsonData);

        // Ambil deskripsi cuaca dan suhu dari JSON
        String deskripsiCuaca = jsonObjek.getJSONArray("weather").getJSONObject(0).getString("description");
        String kodeIkon = jsonObjek.getJSONArray("weather").getJSONObject(0).getString("icon"); // Point 1: Dapatkan kode ikon cuaca
        double suhu = jsonObjek.getJSONObject("main").getDouble("temp");

        // Set teks deskripsi cuaca dan suhu ke JLabel
        jKetCuaca.setText(deskripsiCuaca);
        jSuhu.setText(suhu + "°C");

        // Buat URL untuk ikon cuaca berdasarkan kode ikon
        String urlIkon = "http://openweathermap.org/img/wn/" + kodeIkon + "@2x.png";

        try {
            // Load ikon dari URL dan set ke JLabel
            ImageIcon ikonCuaca = new ImageIcon(new URL(urlIkon));
            iconC.setIcon(ikonCuaca);
        } catch (Exception e) {
            e.printStackTrace();
            iconC.setText("Gagal memuat ikon cuaca");
        }
    }
    
    private void MenyimpanKotaKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cities.txt"))) {
            for (int i = 0; i < comboKota.getItemCount(); i++) {
                writer.write(comboKota.getItemAt(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void KotaKeFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("cities.txt"))) {
            String city;
            while ((city = reader.readLine()) != null) {
                comboKota.addItem(city);
            }
        } catch (IOException e) {
            // Jika file tidak ditemukan, abaikan saja
            System.out.println("No saved cities found.");
        }
    }
    
    
      private void SaveDataCuacaKeFile(String cityName, String weatherDescription, double temperature) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("weather_data.csv", true))) {
            // Menuliskan data dalam format CSV
            writer.write(cityName + "," + weatherDescription + "," + temperature + "°C");
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Data cuaca berhasil disimpan!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan data cuaca.");
            e.printStackTrace();
        }
    }
      
      private void hapusKota() {
    String selectedCity = (String) comboKota.getSelectedItem();
    if (selectedCity != null) {
        comboKota.removeItem(selectedCity);
        MenyimpanKotaKeFile(); // Simpan perubahan setelah kota dihapus
        JOptionPane.showMessageDialog(this, "Kota " + selectedCity + " berhasil dihapus dari daftar favorit.");
    } else {
        JOptionPane.showMessageDialog(this, "Tidak ada kota yang dipilih untuk dihapus.");
    }
}
      
      
      
    public CekCuacaFrame() {
        initComponents();
        
       System.out.println("C:\\Users\\ASUS\\Documents\\NetBeansProjects\\AplikasiCekCuaca_Tugas6\\Simpan " + System.getProperty("user.dir"));
        KotaKeFile();
        
        // Inisialisasi model tabel
        tableModel = new DefaultTableModel(new String[]{"Nama Kota", "Cuaca", "Suhu"}, 0);
        TabelData.setModel(tableModel);    
    }
    public void itemStateChanged(java.awt.event.ItemEvent evt) throws JSONException {
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedCity = (String) comboKota.getSelectedItem();
            MenampilkanCuaca(selectedCity);  // Panggil metode untuk menampilkan cuaca kota yang dipilih
        }
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        iconC = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jKetCuaca = new javax.swing.JLabel();
        jSuhu = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelData = new javax.swing.JTable();
        InputLokasiCuaca = new javax.swing.JTextField();
        comboKota = new javax.swing.JComboBox<>();
        bDelete = new javax.swing.JButton();
        bcekCuaca = new javax.swing.JButton();
        bSave = new javax.swing.JButton();
        bInput = new javax.swing.JButton();
        Exit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel2.setText("DATA DAN CEK HASIL CUACA");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("NAMA KOTA / TEMPAT");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("DAFTAR KOTA FAVORIT");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("CUACA");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setText("KETERANGAN CUACA");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("SUHU");

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel11.setText("DATA CUACA");

        TabelData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nama Kota", "Cuaca", "Suhu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TabelData);

        comboKota.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboKotaItemStateChanged(evt);
            }
        });

        bDelete.setBackground(new java.awt.Color(255, 153, 153));
        bDelete.setText("DELETE");
        bDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteActionPerformed(evt);
            }
        });

        bcekCuaca.setBackground(new java.awt.Color(255, 255, 204));
        bcekCuaca.setText("CEK CUACA");
        bcekCuaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcekCuacaActionPerformed(evt);
            }
        });

        bSave.setBackground(new java.awt.Color(255, 255, 204));
        bSave.setText("SIMPAN TEMPAT FAVORIT");
        bSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveActionPerformed(evt);
            }
        });

        bInput.setBackground(new java.awt.Color(255, 255, 204));
        bInput.setText("INPUT DATA KE TABEL");
        bInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInputActionPerformed(evt);
            }
        });

        Exit.setText("KELUAR");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(569, 569, 569)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Exit))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(InputLokasiCuaca, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(77, 77, 77))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(bcekCuaca)
                                .addGap(28, 28, 28)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bDelete)
                            .addComponent(jLabel4))
                        .addGap(33, 33, 33)
                        .addComponent(comboKota, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(bSave)
                        .addGap(0, 196, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(151, 151, 151)
                                .addComponent(jSuhu))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5))
                                .addGap(42, 42, 42)
                                .addComponent(jKetCuaca))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(341, 341, 341)
                        .addComponent(iconC, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bInput)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addGap(276, 276, 276)))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(Exit)))
                        .addGap(70, 70, 70)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(InputLokasiCuaca, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(comboKota, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bSave))
                        .addGap(28, 28, 28)
                        .addComponent(bDelete)
                        .addGap(49, 49, 49))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(bcekCuaca)
                        .addGap(29, 29, 29)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 41, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(bInput)
                        .addGap(99, 99, 99))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(46, 46, 46)
                        .addComponent(iconC, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jKetCuaca))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jSuhu))
                        .addGap(129, 129, 129))))
        );

        jPanel2.setBackground(new java.awt.Color(251, 94, 142));

        jLabel1.setFont(new java.awt.Font("Stencil", 1, 48)); // NOI18N
        jLabel1.setText("CEK CUACA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(470, 470, 470)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel1)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bcekCuacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcekCuacaActionPerformed
       // Action untuk tombol "Cek Cuaca"
     String cityName = InputLokasiCuaca.getText();
        if (!cityName.isEmpty()) {
         try {
             MenampilkanCuaca(cityName);
             
             boolean exists = false;
             for (int i = 0; i < comboKota.getItemCount(); i++) {
                 if (comboKota.getItemAt(i).equalsIgnoreCase(cityName)) {
                     exists = true;
                     break;
                 }
             }
             
             if (!exists) {
                 comboKota.addItem(cityName);
                 MenyimpanKotaKeFile(); // Simpan kota ke file setelah penambahan
             }
         } catch (JSONException ex) {
             Logger.getLogger(CekCuacaFrame.class.getName()).log(Level.SEVERE, null, ex);
         }
        } else {
            JOptionPane.showMessageDialog(this, "Silakan masukkan nama kota!");
        }
    }//GEN-LAST:event_bcekCuacaActionPerformed

    private void comboKotaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboKotaItemStateChanged
         if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedCity = (String) comboKota.getSelectedItem();
            InputLokasiCuaca.setText(selectedCity); // Mengisi txtCityName dengan kota yang dipilih
        }
    }//GEN-LAST:event_comboKotaItemStateChanged

    private void bInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInputActionPerformed
        tableModel.setRowCount(0); // Hapus data lama di tabel sebelum memuat data baru

        try (BufferedReader reader = new BufferedReader(new FileReader("weather_data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    // Menambahkan data ke dalam tabel
                    tableModel.addRow(new Object[]{data[0], data[1], data[2]});
                }
            }
            JOptionPane.showMessageDialog(this, "Data cuaca berhasil dimuat ke tabel.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat data cuaca.");
            e.printStackTrace();
        }    
    }//GEN-LAST:event_bInputActionPerformed

    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
      // Action untuk tombol "Simpan ke Favorit"
        String cityName = InputLokasiCuaca.getText();
        String weatherDescription = jKetCuaca.getText();
        String temperature = jSuhu.getText();

        if (!cityName.isEmpty() && !weatherDescription.isEmpty() && !temperature.isEmpty()) {
            SaveDataCuacaKeFile(cityName, weatherDescription, Double.parseDouble(temperature.replace("°C", "")));
        } else {
            JOptionPane.showMessageDialog(this, "Data cuaca tidak lengkap. Pastikan semua data sudah terisi.");
        }
    }//GEN-LAST:event_bSaveActionPerformed

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        // Tambahkan action listener untuk tombol keluar
        Exit.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.exit(0); // Menutup aplikasi
        }
    });
    }//GEN-LAST:event_ExitActionPerformed

    private void bDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteActionPerformed
        // Tambahkan action listener untuk tombol hapus
    bDelete.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            hapusKota();
        }
    });
    }//GEN-LAST:event_bDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CekCuacaFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Exit;
    private javax.swing.JTextField InputLokasiCuaca;
    private javax.swing.JTable TabelData;
    private javax.swing.JButton bDelete;
    private javax.swing.JButton bInput;
    private javax.swing.JButton bSave;
    private javax.swing.JButton bcekCuaca;
    private javax.swing.JComboBox<String> comboKota;
    private javax.swing.JLabel iconC;
    private javax.swing.JLabel jKetCuaca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jSuhu;
    // End of variables declaration//GEN-END:variables
}
