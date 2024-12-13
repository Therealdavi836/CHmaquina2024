/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package CHmaquinaCodigo;

/**
 * @title: CHAmbiente.java
 * @description: Editor de texto para verificacion y correccion de sintaxis ch 
 * @date: 
 * @version: 1.0.0
 * @author: Juan David Fajardo Betancourt
 */

import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CHAmbiente extends javax.swing.JFrame {

    /**
     * Creates new form CHAmbiente
     */
    
    String ruta = "";
    String nombrea="";
    CHEntrada archivo=new CHEntrada();
    int programa=archivo.programa;
    
    
    public CHAmbiente() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false); 
        setTitle("Mi CH-Maquina Editor");
        setIconImage(new ImageIcon(getClass().getResource("/CHimagenes/icono.png")).getImage());//icono de la ventana del programa
        
        String path = "/CHimagenes/editor.png";  
        URL url = this.getClass().getResource(path);  
        ImageIcon icon = new ImageIcon(url);  
  
        //JLabel label = new JLabel("some text");  
        //label.setIcon(icon);  
        imagen.setIcon(icon); // NOI18N
        
         //Estructura de datos encargada de configurar el fondo de pantalla 
        ((JPanel)getContentPane()).setOpaque(false); 
        ImageIcon uno=new ImageIcon(this.getClass().getResource("/CHimagenes/Fondo2.jpg"));
        JLabel fondo= new JLabel(); 
        fondo.setIcon(uno); 
        getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0,0,uno.getIconWidth(),uno.getIconHeight());
    }
    
  //funcion encargada de leer el archivo y hacer el token
  public void sintaxis(){
      //
      int lexa =0;
      long lNumeroLineas = 0;// INICIALIZA EL CONTADOR DE LAS LINEAS DEL ARCHIVO
      // ALMACENARA LA LISTA DELOS ERRORES ENCONTRADOS 
      String  errores= "";
        
      FileReader file2;   
      try{
          
          //Inicializo todas las variables a leer
          //de forma general
            
          String operacion="";// alamcena el primer token  de la linea examinada
            
          String variablenueva="", tipo="", valor="";
          String nombreetiqueta="", numerolinea="";
          String variablealmacene="", variablecargue="";
           
            
          // variables  para calculos matematicos
          String variablesume="";
          String variablereste="";
          String variablemultiplique="";
          String variabledivida="";
          String variablepotencia="";
          String variablemodulo="";
          String variableconcatene="";
          String variableelimine="",variableextraiga="";
            
            
            
          // ciclos
          String etiquetaini="";
          String etiquetainicio="", etiquetafin="";
            
          // entrega de resultados
          String variablemuestre="";
          String variableimprimir="";
            
            
          //operaciones con cadenas
          String variablelea="";
          String contenido=panel.getText();
            
          FileWriter file = null;	 // la extension al archivo 
          try {
              
              file = new FileWriter("editor.txt"); 
              BufferedWriter escribir = new BufferedWriter(file); 
              PrintWriter archivo = new PrintWriter(escribir); 

              archivo.print(contenido); 
              archivo.close(); 
                
          }catch (Exception e) {
              
          }
            
          FileReader file1 = new FileReader("editor.txt");
          BufferedReader leer = new BufferedReader(file1);
          // SE ENCARGA DE RECORRER EL ARCHIVO Y CONTAR LA CANTIDAD DE LINEAS
            
          String sCadena;
          // CICLO QUE RECORRE CADA LINEA  HASTA QUE LA LINEA SEA NULL
          while ((sCadena = leer.readLine())!=null) {
          lNumeroLineas++;
          }
            
          file2 = new FileReader("editor.txt");
          BufferedReader leer2 = new BufferedReader(file2);
           
          // FOR ENCARGADO DE RECORRER  EL ARCHIVO LINEA POR LINEA PARA HACER LOS TOKENS
          for (int i=0; i<lNumeroLineas; i++){
              //
              errores= "**** SUGERENCIAS PARA CORREGIR LOS ERRORES ENCONTRADOS ****\n\n";
              //Se usa 'StringTokenizer' para tomar toda la linea  examinada
                
              String linea=leer2.readLine().trim();
                
              lexa++;
              StringTokenizer tk = new StringTokenizer(linea);
            
              // condiciona la linea para saber si esta vacia
              if (linea.length()>0) {
                  //
                  operacion= (tk.nextToken());
                
              }else{
                  
                  // en caso tal que la linea este vacia  
                    operacion=" ";
                
              }
              // evalua por casos  cada linea y hace los tokens  correspondientes
              switch (operacion) {
                  
                  case "cargue":
                      
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          // hace el segundo token de la linea
                          variablecargue= (tk.nextToken());
                            
                          break;
                          
                      }else{
                          
                          errores=errores+"CARGUE debe tener dos argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                            
                  
                  case "almacene":
                      
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          // hace el segundo token de la linea
                          variablealmacene= (tk.nextToken());
                          //agrega en el array list de instrucciones
                            
                           
                          break;
                      }else{
                          //
                          errores=errores+"ALAMCENE debe tener dos argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                            
                  case "vaya":
                      //
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                      // hace el segundo token de la linea
                      etiquetaini= (tk.nextToken());
                      //agrega en el array list de instrucciones
                            
                      break;
                      }else{
                          //
                          errores=errores+"VAYA debe tener dos argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                        
                  case "vayasi":
                      
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==2) {
                          
                          // hace el segundo token de la linea
                          etiquetaini= (tk.nextToken());
                          etiquetafin=(tk.nextToken());
                            
                          break;  
                      }else{
                          
                          errores=errores+"VAYASI debe tener tres argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                            
                  case "nueva":
                            
                      if (tk.countTokens()>1 && tk.countTokens()<4) {
                          
                          System.out.println("cantidad de tokens "+tk.countTokens());
                          // hace el segundo token de la linea
                          variablenueva= (tk.nextToken());
                          tipo=(tk.nextToken());
                          if (tk.countTokens()<1) {
                              
                              if ("c".equals(variablenueva) || "C".equals(variablenueva)  ) {
                                  
                                  valor=" ";
                              }else{
                                  
                                  valor="0";
                              }
                          }else{
                              //
                              valor= (tk.nextToken());
                          }
                             
                          switch (tipo){
                              
                              case "i":
                                  
                                  case "I":
                                      
                                      tipo="ENTERO";
                                      break;
                                
                              case "r":
                                      
                                  case "R":
                                      
                                      tipo="REAL";
                                      break;
                                            
                              case "c":
                                  
                                  case "C":
                                      
                                      tipo="CADENA";
                                      break;
                          default:
                              
                              errores= errores + "* hay un error de sintaxis en la linea "+lNumeroLineas+"\n"+
                                                                         "parece error en el tipo de variable";
                              file2.close();
                                    
                                    
                          }
                            
                          break; 
                      }else{
                          
                                errores=errores+"NUEVA debe tener tres  o cuatro argumentos en esta linea";
                                file2.close();
                                throw new Exception("Invalid entry");
                            }
                            
                        
                  case "etiqueta":
                      
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==2) {
                          
                          // hace el segundo token de la linea
                          nombreetiqueta =(tk.nextToken());
                          numerolinea=(tk.nextToken());
                              
                          break;
                      }else{
                          
                          errores=errores+"ETIQUETA debe tener tres   argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                        
                  case "lea":
                      
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variablelea=(tk.nextToken());
                            
                          break;
                      }else{
                          
                          errores=errores+"LEA debe tener tres   argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                        
                  case "sume":
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          variablesume=(tk.nextToken());
                            
                           
                          break;
                      }else{
                          
                          errores=errores+"SUME debe tener dos   argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                            
                  case "reste":
                      
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          
                          variablereste=(tk.nextToken());
                             
                          break;
                      }else{
                          //
                          errores=errores+"RESTE debe tener dos   argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                            
                  case "multiplique":
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variablemultiplique=(tk.nextToken());
                             
                          break;
                      }else{
                          
                          errores=errores+"MULTIPLIQUE debe tener dos argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                         
                  case "divida":
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variabledivida=(tk.nextToken());
                             
                          break;
                      }else{
                          
                          errores=errores+"DIVIDA debe tener dos   argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                            
                  case "potencia":
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          variablepotencia=(tk.nextToken());
                            
                          break;
                      }else{
                          
                          errores=errores+"POTENCIA debe tener dos   argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                        
                  case "modulo":
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variablemodulo=(tk.nextToken());
                            
                          break;
                      }else{
                          
                          errores=errores+"MODULO debe tener dos   argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                            
                  case "concatene":
                      
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          variableconcatene=(tk.nextToken());
                            
                          break;
                      }else{
                           errores=errores+"CONCATENE debe tener dos   argumentos en esta linea";
                           file2.close();
                           throw new Exception("Invalid entry");
                      }
                            
                  case "elimine":
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          variableelimine=(tk.nextToken());
                            
                          break;
                      }else{
                           errores=errores+"ELIMINE debe tener dos   argumentos en esta linea";
                           file2.close();
                           throw new Exception("Invalid entry");
                      }
                        
                  case "extraiga":
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variableextraiga=(tk.nextToken());
                            
                          break;
                      }else{
                          
                          errores=errores+"EXTRAIGA debe tener dos   argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                            
                  case "muestre":
                      
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          variablemuestre=(tk.nextToken());
                            
                          break;
                      }else{
                          errores=errores+"MUESTRE debe tener dos   argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                            
                  case "imprima":
                      
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variableimprimir=(tk.nextToken());
                            
                          break;
                      }else{
                          
                          errores=errores+"IMPRIMA debe tener dos   argumentos en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                            
                  case "retorne":
                      
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==0 || tk.countTokens()==1) {
                          //
                            
                          break;
                      }else{
                          
                          errores=errores+"RETORNE debe tener uno argumento o dos, y este debe ser 0 en esta linea";
                          file2.close();
                          throw new Exception("Invalid entry");
                      }
                            
                  case "//":
                           
                      break;
                        
                  case " ":
                            
                      break;
                            
                  default:
                              
                      // hace el llamado a la exeption si algo esta mal en el archivo
                      errores=errores+"esta linea no cumple con ninguna de las reglas de sintaxis de .ch";
                      file2.close();
                      throw new Exception("Invalid entry");
               }
          }
          file2.close();
          File temp = new File("editor.txt");
          temp.delete();
          //Messaje que se muestra cuando todo salio bien en el  'try'
          JOptionPane.showMessageDialog(null, "EL ANALISIS DE SINTAXIS FUE EXISTOSO NO SE IDENTIFICO NINGUN ERROR");
            
            
            
            
      }catch(Exception e){
          
          File temp = new File("editor.txt");
          temp.delete();
              
          //Messaje que se muestra cuando hay error dentro del 'try'
          JOptionPane.showMessageDialog(null, "TENEMOS UN INCONVENIENTE:\n"
                                     + "Se generó un error al cargar el archivo en la\n"
                                    + "linea "+lexa+" es posible que uno de los datos\n"
                                          + "del archivo no coincida con el formato\n\n"
                                          + errores);
            
           
      }
  }
  
  //Funcion definida para guardar un programa con extension ch 
  public void guardar(){
        javax.swing.JFileChooser jF1= new javax.swing.JFileChooser(); 
        ruta = ""; 
        try{ 
            if(jF1.showSaveDialog(null)==jF1.APPROVE_OPTION){ 
            ruta = jF1.getSelectedFile().getAbsolutePath(); 
            //Aqui ya tiens la ruta,,,ahora puedes crear un fichero n esa ruta y escribir lo k kieras... 
            String text = panel.getText();
            nombrea=jF1.getName();
            String nombreArchivo= ruta; // Aqui se le asigna el nombre 
            
            FileWriter file = null;	 // la extension al archivo 
            try { 
                file = new FileWriter(nombreArchivo); 
                BufferedWriter escribir = new BufferedWriter(file); 
                PrintWriter archivo = new PrintWriter(escribir); 

                archivo.print(text); 
                archivo.close(); 
                
                JOptionPane.showMessageDialog(null,"SE HA CREADO EL NUEVO ARCHIVO ");
            } 
                catch (Exception e) { 
            }
            } 
        }catch (Exception ex){
            
            ex.printStackTrace(); 
        } 
  }
       
    
    public void cargararchivo(){
      // encargado de abrir el panel de busqueda de archivos y cargarlo a la funcion actualizar.
        JFileChooser ventana = new JFileChooser();
        // filtra las extenciones segun la que buscamos
        ventana.setFileFilter(new FileNameExtensionFilter("todos los archivos "
                                                  + "*.ch", "CH","ch"));
        int sel = ventana.showOpenDialog(CHAmbiente.this);
         // condicional que le dara el nombre a el programa
        String prefijo;
        if (programa<10) {
             prefijo="000"+String.valueOf(programa);
            programa++;
        }else{
             prefijo="00"+String.valueOf(programa);
        }
        
        if (sel == JFileChooser.APPROVE_OPTION) {

            File file = ventana.getSelectedFile();
            
            String nombrea=file.getName();
            actualizar(file.getPath(), prefijo,nombrea );

        }
  }
    
  // funcion encargada de leer el archivo y hacer el token
  public void actualizar(String url, String pre,String nombre){
      //
      
      try{
          //
             
          // leee el archivo y lo carga en bufer
          FileReader file = new FileReader(url);
          BufferedReader leer = new BufferedReader(file);
          String sCadena;
          String contenido="";
          // CICLO QUE RECORRE CADA LINEA  HASTA QUE LA LINEA SEA NULL
          while ((sCadena = leer.readLine())!=null) {
              
              // crea el contenido del  .ch
              contenido=contenido+sCadena+"\r\n";
          }
          panel.setText(contenido);// ingresa el contenido del archivo al panel
          contenido="";
      } catch (Exception ex){
          
          ex.printStackTrace(); 
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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panel = new javax.swing.JTextArea();
        cargar = new javax.swing.JButton();
        guardar = new javax.swing.JButton();
        limpiar1 = new javax.swing.JButton();
        limpiar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        terminar = new javax.swing.JButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton6 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        jToggleButton5 = new javax.swing.JToggleButton();
        imagen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Editor del CH-Maquina");

        panel.setColumns(20);
        panel.setLineWrap(true);
        panel.setRows(5);
        panel.setWrapStyleWord(true);
        jScrollPane1.setViewportView(panel);

        cargar.setText("Cargar Programa ch");
        cargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarActionPerformed(evt);
            }
        });

        guardar.setText("Guardar ");
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });

        limpiar1.setText("Verificar sintaxis");
        limpiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiar1ActionPerformed(evt);
            }
        });

        limpiar.setText("Limpiar ");
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });

        jButton1.setText("Imprimir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        terminar.setText("Terminar");
        terminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                terminarActionPerformed(evt);
            }
        });

        jToggleButton2.setText("?");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jToggleButton1.setText("?");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton6.setText("?");
        jToggleButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton6ActionPerformed(evt);
            }
        });

        jToggleButton3.setText("?");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        jToggleButton4.setText("?");
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });

        jToggleButton5.setText("?");
        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cargar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(limpiar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(limpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(terminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jToggleButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jToggleButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jToggleButton3)
                            .addComponent(jToggleButton4)
                            .addComponent(jToggleButton5))
                        .addGap(106, 106, 106))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cargar)
                    .addComponent(jToggleButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guardar)
                    .addComponent(jToggleButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(limpiar1)
                    .addComponent(jToggleButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(limpiar)
                    .addComponent(jToggleButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jToggleButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(terminar)
                    .addComponent(jToggleButton5))
                .addGap(41, 41, 41))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarActionPerformed
        //boton encargado de cargar un archivo a editar.
        cargararchivo();
        // TODO add your handling code here:
    }//GEN-LAST:event_cargarActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        // TODO add your handling code here:
        // boton encargado de guardar el archivo editado
        guardar();
    }//GEN-LAST:event_guardarActionPerformed

    private void limpiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiar1ActionPerformed
        // TODO add your handling code here:
        sintaxis();
    }//GEN-LAST:event_limpiar1ActionPerformed

    private void limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarActionPerformed
        // boton encargado de limpiar el editor
        panel.setText("");
    }//GEN-LAST:event_limpiarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //se encarga de imprimir el contenido de la impresora
        String print =panel.getText();
        panel.setText(print);
        System.out.println("imprimira el panel de editor "+print);
        try {

            panel.print();
        } catch (PrinterException ex) {

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void terminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_terminarActionPerformed
        //boton encargado de cerrar el editor
        dispose();
    }//GEN-LAST:event_terminarActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
        // boton encargado mostar  informacion del boton cargar archivo
        JOptionPane.showOptionDialog(this, "ESTE BOTON SE ENCARGA DE  ABRIR EL ASISTENTE  DE BUSQUEDA DE ARCHIVOS \n"
            + "PARA QUE CARGUE  EL *.CH QUE DESE EDITAR  O SIMPLEMENTE VISUALIZAR.", "INFORMACION",
            JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{" OK "},"OK");
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        // boton encargado mostar  informacion del boton guardar
        JOptionPane.showOptionDialog(this, "ESTE BOTON SE ENCARGA DE  ABRIR EL ASISTENTE  DE GUARDADO DE ARCHIVOS \n"
            + "Y HAGA UNA COPIA  DEL CONTENIDO DEL PANEL DEL EDITOR  PARA LUEGO SER \n"
            + "CARGADO EN EL CH-MAQUINA.", "INFORMACION",
            JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{" OK "},"OK");
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        // TODO add your handling code here:
        // boton encargado mostar  informacion del boton limpiar
        JOptionPane.showOptionDialog(this, "ESTE BOTON SE ENCARGA BORRAR TODO EL CONTENIDO DEL PANEL  \n", "INFORMACION",
            JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{" OK "},"OK");
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
        // TODO add your handling code here:
        // boton encargado mostar  informacion del boton IMPRIMIR
        JOptionPane.showOptionDialog(this, "ESTE BOTON SE ENCARGA DE  ABRIR EL ASISTENTE  DE IMPRESION DE ARCHIVOS \n"
            + "Y TOMA  UNA COPIA  DEL CONTENIDO DEL PANEL DEL EDITOR  PARA LUEGO SER \n"
            + "IMPRESO Y GUARDADO EN ARCHIVO SI ES DE SU DESEO .", "INFORMACION",
            JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{" OK "},"OK");
    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void jToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton5ActionPerformed
        // TODO add your handling code here:
        // boton encargado mostar  informacion del boton TERMINAR
        JOptionPane.showOptionDialog(this, "ESTE BOTON SE ENCARGA DE CERRAR EL EDITOR  Y REGRESAR A LA INTERFAZ DEL CH-MAQUINA \n", "INFORMACION",
            JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{" OK "},"OK");
    }//GEN-LAST:event_jToggleButton5ActionPerformed

    private void jToggleButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton6ActionPerformed

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
            java.util.logging.Logger.getLogger(CHAmbiente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CHAmbiente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CHAmbiente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CHAmbiente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CHAmbiente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cargar;
    private javax.swing.JButton guardar;
    private javax.swing.JLabel imagen;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton6;
    private javax.swing.JButton limpiar;
    private javax.swing.JButton limpiar1;
    private javax.swing.JTextArea panel;
    private javax.swing.JButton terminar;
    // End of variables declaration//GEN-END:variables
}