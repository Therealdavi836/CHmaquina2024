/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package CHmaquinaCodigo;

/**
 * @title: CHEntrada.java
 * @description: Interfaz grafica de procesos de computador ficticio para lenguaje ch 
 * @date: 
 * @version: 1.0.0
 * @author: Juan David Fajardo Betancourt
 */

//Importaciones de APIS de interfaz grafica 
import java.awt.Desktop;
import java.awt.print.PrinterException;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

//Clase principal que almacena el funcionamiento total del CHmaquina
public class CHEntrada extends javax.swing.JFrame {

    /** Creates new form CHEntrada */
    
    //Definicion de variables globales 
    DefaultTableModel modelo,tprocesos,tvariables, tetiquetas;
    String arc = "Documentacion tecnica CHmaquina.docx";
    int programa=1; // cantidad de programas cargados
    String memoriaprin[]; // vector de memoria principal
    public static ArrayList<String> instrucciones;
    public static ArrayList<String> nvariables;
    public static ArrayList<Object[]> var;
    public static ArrayList<Object[]> etiq;
    public static ArrayList<String[]> resultados;
    
    //Variables numericas para gestion de tablas globales 
    int pivote=0;
    int rlp;
    int inicialproceso=0, inicialvariables=0,inicialetiquetas=0;
    int llegada =1;
    
    //Constructor principal para el funcionamiento de la maquina 
    public CHEntrada() {
        initComponents();
        instrucciones=new ArrayList();
        nvariables=new ArrayList();
        var=new ArrayList();
        etiq=new ArrayList();
        setLocationRelativeTo(null);
        setResizable(true); //Permite que la ventana principal se pueda maximizar o minimizar
        setTitle("Mi CH-maquina"); //Titulo de la ventana desplegada del programa
        setIconImage(new ImageIcon(getClass().getResource("/CHimagenes/icono.png")).getImage());// icono de la ventana del programa
        cargarprograma.setEnabled(false);//Impide cargar programas sin encender la maquina
        //Impide apagar la maquina sin encenderla
        apagarmaquina1.setEnabled(false);
        apagarmaquina2.setEnabled(false);
        //Desactiva botones que no pueden ser inicializados sin orden previa
        botoncargar.setEnabled(false);
        ejecutar.setVisible(false);
        editor.setVisible(false);
        pasoapaso.setVisible(false);
        IMP.setEnabled(false);
        EJEC.setEnabled(false);
        resultados=new ArrayList();
        
        //Estructura de datos encargada de configurar el fondo de pantalla 
        ((JPanel)getContentPane()).setOpaque(false); 
        ImageIcon uno=new ImageIcon(this.getClass().getResource("/CHimagenes/Fondo1.jpg"));
        JLabel fondo= new JLabel(); 
        fondo.setIcon(uno); 
        getLayeredPane().add(fondo,JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0,0,uno.getIconWidth(),uno.getIconHeight());
        
        //se define los valores por defecto de la memoria del kernel y la memoria disponible para programas
        int maxmemo=3100, maxkerner=1000;
        memoria.setModel(new javax.swing.SpinnerNumberModel(100, 2, maxmemo, 1));
        kernel.setModel(new javax.swing.SpinnerNumberModel(20, 1, maxkerner, 1));
        total_memoria.setText("80");

        //Crea el tipo de modelo de tabla para mapa de memoria
        modelo = new DefaultTableModel();
        tabla.setModel(modelo);
        //Crean los nombres de las columnas 
        modelo.addColumn("POS-MEMO");
        modelo.addColumn("PROGRAMA");
        modelo.addColumn("INSTRUCCION");
        modelo.addColumn("ARGUMENTO");
        modelo.addColumn("VALOR");
        
        //redimenciona la columna
        TableColumn columna = tabla.getColumn("POS-MEMO");
        columna.setPreferredWidth(80);// pixeles por defecto
        columna.setMinWidth(50);//pixeles minimo
        columna.setMaxWidth(90);// pixeles maximo
        
        TableColumn PRE = tabla.getColumn("PROGRAMA");
        PRE.setPreferredWidth(80);// pixeles por defecto
        PRE.setMinWidth(10);//pixeles minimo
        PRE.setMaxWidth(200);// pixeles maximo
        
        TableColumn PREE = tabla.getColumn("INSTRUCCION");
        PREE.setPreferredWidth(120);// pixeles por defecto
        PREE.setMinWidth(10);//pixeles minimo
        PREE.setMaxWidth(200);// pixeles maximo
        
        TableColumn PREEE = tabla.getColumn("VALOR");
        PREEE.setPreferredWidth(80);// pixeles por defecto
        PREEE.setMinWidth(10);//pixeles minimo
        PREEE.setMaxWidth(200);// pixeles maximo
        
        //Crea el tipo de modelo de tabla para procesos
        tprocesos = new DefaultTableModel();
        tabla2.setModel(tprocesos);
        // Crea los nombres de las columnas 
        tprocesos.addColumn("ID");
        tprocesos.addColumn("PROGRAMAS");
        tprocesos.addColumn("#INST");
        tprocesos.addColumn("RB");
        tprocesos.addColumn("RLC");
        tprocesos.addColumn("RLP");
        tprocesos.addColumn("PRIO");
        tprocesos.addColumn("T LLEG");
        
        //redimenciona la columna
        TableColumn id = tabla2.getColumn("ID");
        id.setPreferredWidth(40);// pixeles por defecto
        id.setMinWidth(10);//pixeles minimo
        id.setMaxWidth(41);// pixeles maximo
        
        TableColumn pro = tabla2.getColumn("PROGRAMAS");
        pro.setPreferredWidth(100);// pixeles por defecto
        pro.setMinWidth(10);//pixeles minimo
        pro.setMaxWidth(501);// pixeles maximo
        
        TableColumn ins = tabla2.getColumn("#INST");
        ins.setPreferredWidth(50);// pixeles por defecto
        ins.setMinWidth(10);//pixeles minimo
        ins.setMaxWidth(51);// pixeles maximo
        
        TableColumn rb = tabla2.getColumn("RB");
        rb.setPreferredWidth(40);// pixeles por defecto
        rb.setMinWidth(10);//pixeles minimo
        rb.setMaxWidth(41);// pixeles maximo
        
        TableColumn rcl = tabla2.getColumn("RLC");
        rcl.setPreferredWidth(40);// pixeles por defecto
        rcl.setMinWidth(10);//pixeles minimo
        rcl.setMaxWidth(41);// pixeles maximo
        
        TableColumn rlp = tabla2.getColumn("RLP");
        rlp.setPreferredWidth(40);// pixeles por defecto
        rlp.setMinWidth(10);//pixeles minimo
        rlp.setMaxWidth(41);// pixeles maximo
        
        TableColumn prio = tabla2.getColumn("PRIO");
        prio.setPreferredWidth(40);// pixeles por defecto
        prio.setMinWidth(10);//pixeles minimo
        prio.setMaxWidth(41);// pixeles maximo
        
        TableColumn tt = tabla2.getColumn("T LLEG");
        tt.setPreferredWidth(40);// pixeles por defecto
        tt.setMinWidth(10);//pixeles minimo
        tt.setMaxWidth(51);// pixeles maximo
        
        //Creal el tipo de modelo de tabla para variables
        tvariables = new DefaultTableModel();
        tablavariables.setModel(tvariables);
        // Crea los nombres de las columnas 
        tvariables.addColumn("POS");
        tvariables.addColumn("PROG");
        tvariables.addColumn("TIPO");
        tvariables.addColumn("VARIABLES");
        tvariables.addColumn("VALOR");
        
        //redimenciona la columna
        TableColumn POS = tablavariables.getColumn("POS");
        POS.setPreferredWidth(40);// pixeles por defecto
        POS.setMinWidth(40);//pixeles minimo
        POS.setMaxWidth(41);// pixeles maximo
        
        TableColumn prog = tablavariables.getColumn("PROG");
        prog.setPreferredWidth(60);// pixeles por defecto
        prog.setMinWidth(10);//pixeles minimo
        prog.setMaxWidth(61);// pixeles maximo
        
        TableColumn vLr = tablavariables.getColumn("VALOR");
        vLr.setPreferredWidth(60);// pixeles por defecto
        vLr.setMinWidth(10);//pixeles minimo
        vLr.setMaxWidth(61);// pixeles maximo
        
        //Crea el tipo de modelo de tabla para etiquetas
        tetiquetas = new DefaultTableModel();
        tablaetiquetas.setModel(tetiquetas);
        
        // Crea los nombres de las columnas
        tetiquetas.addColumn("POS");
        tetiquetas.addColumn("PROG");
        tetiquetas.addColumn("ETIQUETAS");
        tetiquetas.addColumn("ARGUMENTO");
        
        //redimenciona la columna
        TableColumn POSS = tablaetiquetas.getColumn("POS");
        POSS.setPreferredWidth(50);// pixeles por defecto
        POSS.setMinWidth(40);//pixeles minimo
        POSS.setMaxWidth(90);// pixeles maximo
        
        TableColumn POG = tablaetiquetas.getColumn("PROG");
        POG.setPreferredWidth(50);// pixeles por defecto
        POG.setMinWidth(40);//pixeles minimo
        POG.setMaxWidth(90);// pixeles maximo
        
        TableColumn POGG = tablaetiquetas.getColumn("ETIQUETAS");
        POGG.setPreferredWidth(70);// pixeles por defecto
        POGG.setMinWidth(40);//pixeles minimo
        POGG.setMaxWidth(90);// pixeles maximo
              
        //Evita editar el contenido de los jtextpanel
        monitor.setEditable(false);
        impresora.setEditable(false);
    }
    
    //Funcion encargada de capturar los valores de kernel y memoria y mostrar la cantidad de 
    //memoria que queda disponible para la asignacion de los programas
    public void memtotal(){
        int mem = (int) memoria.getValue();
        int ker = (int) kernel.getValue();
        int total= mem - ker;
        total_memoria.setText(String.valueOf(total));
        
    }

   //Funcion para reproducir sonidos 
   public Clip clip;
   public String ruta="/audio/";
    
   //Funcion encargada de ejcutar la reproduccion de sonidos 
   public void son(String archivo){
      //carga en buffer el archivo de audio
      BufferedInputStream Mystream = new BufferedInputStream(getClass().getResourceAsStream(ruta+archivo+".wav")); 
    
      try{
          //Ejecuta el audio 
           AudioInputStream song = AudioSystem.getAudioInputStream(Mystream); 
           Clip sonido = AudioSystem.getClip(); 
           sonido.open(song); 
           sonido.start();
      }catch(Exception e){
            
      }
   }
   
   //Funcion encargada de encender la maquina y cargar la memoria
  public void encender(){
        //hace el llamado de funcion para que reproduzca el sonido de encendido 
        //desactiva los spinner
        kernel.setEnabled(false);
        memoria.setEnabled(false);
        encender.setEnabled(false);
        encender2.setEnabled(false);
        cargarprograma.setEnabled(true);
        apagarmaquina1.setEnabled(true);
        apagarmaquina2.setEnabled(true);
        botoncargar.setEnabled(true);
        estado.setText("MODO KERNEL");
        editor.setVisible(true);
        
         //sonidoencender("inicio");
        son("inicio");
        rlp = (int)kernel.getValue()+1; // inicualiza el primer rcl
        
        //Instancia el objeto para llenar la taBla de valores 
        Object []object = new Object[5];
        //inicializa la memoria principal con el tamaño de memoria establecido
        memoriaprin= new String[(int)memoria.getValue()];
        //Valores por defecto de la primera posicion del mapa de memoria 
        object[0]="0";
        object[1]="0000";
        object[2]="----";
        object[3]="acumulador";
        object[4]="0";
        memoriaprin[0]="acumulador";// carga en la memoria  
        modelo.addRow(object);
     
        // Ciclos encargados de llenar el mapa de memoria
        int contador = 0;

        int mem = Integer.parseInt(total_memoria.getText());
        int ker = (int) kernel.getValue();
        for (int i = 0; i < ker; i++) {
            contador++;
            object[0]=String.valueOf(contador);
            object[1]="0000";
            object[2]="----";
            object[3]="-----sistema operativo-----";
            object[4]="----";
            modelo.addRow(object);
            memoriaprin[i+1]="-----sistema operativo-----";
        }
        
        pivote = pivote + ker +1; //crea un pivote marcador de inicio de primer programa
        for (int i = 0; i <mem-1; i++) {
            contador++;
            object[0]=String.valueOf(contador);
            object[1]="";
            object[2]="";
            object[3]="";
            object[4]="";
            modelo.addRow(object);
        }
        
        
        //se encarga de crear el contenido de un programa por defecto en la tabla de procesos 
        Object []objectprocesos = new Object[6];
        objectprocesos[0]="0000";//numero de instancias
        objectprocesos[1]="Sistema operativo ch-maquina"; //nombre del programa
        objectprocesos[2]=ker; //numero de lineas del programa
        objectprocesos[3]=1; //rb
        objectprocesos[4]=ker; //registro limite de el programa
        objectprocesos[5]=ker+1 ; //crea el rlp
        // objectprocesos[6]="oo";
        // objectprocesos[7]="oo";
        tprocesos.addRow(objectprocesos);// adiciona a la tabla    
  }
  
  //Se encarga de borrar los archivos temporales
  public void temporales(){
      File temp = new File(arc);
        temp.delete();
  }
  
  //Funcion encargada de apagar la maquina y regresarla a su estado inicial
  public void apagar(){
      //codigo encargado de apagar la maquina y regresarla a su estado inicial
      temporales();// borra los archivos temporales
      
      if(JOptionPane.showOptionDialog(this, "¿ESTA SEGURO QUE DESEA APAGAR LA MAQUINA?", "Mensaje de Alerta",
          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{" SI "," NO "},"NO")==0)
      {
          son("cierre");
          //Se encarga de detener un instante el proceso
          try {
              //
              Thread.sleep(2000); //el tiempo es en milisegundos
              
            } catch (InterruptedException ex) {
        }
          //
          setVisible(false);
          new CHEntrada().setVisible(true);
      }else{
          JOptionPane.showMessageDialog(this, "PUEDE CONTINUAR CON LA EJECUCION DEL PROGRAMA");
        }
  }
  
  public void cargararchivo(){
      //encargado de abrir el panel de busqueda de archivos y cargarlo a la funcion actualizar.
      JFileChooser ventana = new JFileChooser();
      //filtra las extenciones segun la que buscamos
      ventana.setFileFilter(new FileNameExtensionFilter("todos los archivos "
                                                  + "*.ch", "CH","ch"));
      int sel = ventana.showOpenDialog(CHEntrada.this);
        
      //incrementan en uno el contador 
      inicialproceso++;
        
        
      //condicional que le dara el nombre a el programa
      String prefijo;
      if (programa<10) {
          //
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
  
  //Funcion encargada de leer el archivo y hacer el token
  public void actualizar(String url, String pre,String nombre){
      // carga en el sistema la prioridad que tendra el programa
      int prioridad=0;
      SpinnerNumberModel sModel = new SpinnerNumberModel(1, 1, 100, 1);
      JSpinner spinner = new JSpinner(sModel);
      int pri = JOptionPane.showOptionDialog(null, spinner, "INGRESE VALOR DE LA PRIORIDAD", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
      if (pri == JOptionPane.OK_OPTION){
          
          prioridad = (int) spinner.getValue();
          
      }
       
      int lexa =0;
      long lNumeroLineas = 0;//Inicializa el contador de las lineas del archivo
      //Almacenara la lista de los errores encontrados 
      
      String  errores= "";
      try{
          //
          instrucciones.clear();
          nvariables.clear();
          //limpia los arreglos para que no queden rastros del programa anterior
          etiq.clear();
          var.clear();
          //lee el archivo y lo carga en el bufer
          FileReader file = new FileReader(url);
          BufferedReader leer = new BufferedReader(file);
            
          //Inicializa todas las variables a leer de forma general
            
          String operacion="";//almacena el primer token de la linea examinada
            
          String variablenueva="", tipo="", valor="";
          String nombreetiqueta="", numerolinea="";
          String variablealmacene="", variablecargue="";
           
            
          //variables para calculos matematicos
          String variablesume="";
          String variablereste="";
          String variablemultiplique="";
          String variabledivida="";
          String variablepotencia="";
          String variablemodulo="";
          String variableconcatene="";
          String variableelimine="",variableextraiga="";
            
            
            
          //ciclos
          String etiquetaini="";
          String etiquetainicio="", etiquetafin="";
            
          //entrega de resultados
          String variablemuestre="";
          String variableimprimir="";
            
            
          //operaciones con cadenas
          String variablelea="";
          
          //Se encarga de recorrer el archivo y contar la cantidad de lineas
            
          String sCadena;
          //Ciclo que recorre cada linea hasta que la linea sea null
          while ((sCadena = leer.readLine())!=null) {
          lNumeroLineas++;
          
          }
           
          FileReader file2 = new FileReader(url);
          BufferedReader leer2 = new BufferedReader(file2);
          
            
          int inicialmemoria=pivote;
          int inicialprocesos= inicialproceso;
          int inicialvariable=inicialvariables; 
          int inicialetiqueta=inicialetiquetas;
            
          int posi=pivote-1; //nos dice en que pisision va almacenando instrucciones
            
            
          //captura la cantidad de filas ocupadas de la tabla d evariables
          int q=tvariables.getRowCount();
          //For encargado de recorrer el archivo linea por linea para hacer los tokens
          for (int i=0; i<lNumeroLineas; i++){
              //
              errores= "**** SUGERENCIAS PARA CORREGIR LOS ERRORES ENCONTRADOS ****\n\n";
              //Se usa 'StringTokenizer' para tomar toda la linea  examinada
              posi++; //aumenta en uno  las posiciones d ememoria para ocupar
              String linea=leer2.readLine().trim();
                
              lexa++;
              StringTokenizer tk = new StringTokenizer(linea);
            
              //condiciona la linea para saber si esta vacia
              if (linea.length()>0) {
                  
                  operacion= (tk.nextToken());
                
              }else{
                  
                  //en caso tal que la linea este vacia  
                  operacion=" ";
                
              }
              //evalua por casos cada linea y hace los tokens  correspondientes
              switch (operacion) {
                  
                  case "cargue":
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          //hace el segundo token de la linea
                          variablecargue= (tk.nextToken());
                                                        
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(variablecargue, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          instrucciones.add(pre + " " + linea);
                          break;
                      }else{
                          errores=errores+"CARGUE debe tener dos argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                            
                        
                  case "almacene":
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                      //hace el segundo token de la linea
                      variablealmacene= (tk.nextToken());
                      //agrega en el array list de instrucciones
                            
                      modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                      modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                      modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                      modelo.setValueAt(variablealmacene, posi, 4);// guarda en la tabla el valor de memoria
                      memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                      instrucciones.add(pre + " " + linea);
                      break;
                      }else{
                          errores=errores+"ALAMCENE debe tener dos argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                            
                  case "vaya":
                      
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          //hace el segundo token de la linea
                          etiquetaini= (tk.nextToken());
                          //agrega en el array list de instrucciones
                            
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(etiquetaini, posi, 4);// guarda en la tabla el valor de memoria
                            
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          instrucciones.add(pre + " " + linea);
                          break;
                      }else{
                          errores=errores+"VAYA debe tener dos argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                        
                  case "vayasi":
                      
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==2) {
                          
                          //hace el segundo token de la linea
                          etiquetaini= (tk.nextToken());
                          etiquetafin=(tk.nextToken());
                          //agrega en el array list de instrucciones
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(etiquetaini+";"+etiquetafin, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          instrucciones.add(pre + " " + linea);
                          break;  
                      }else{
                          errores=errores+"VAYASI debe tener tres argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                            
                  case "nueva":
                            
                      if (tk.countTokens()>1 && tk.countTokens()<4) {
      
                          inicialvariables++;
                          System.out.println("cantidad de tokens "+tk.countTokens());
                          //hace el segundo token de la linea
                          variablenueva= (tk.nextToken());
                                
                          tipo=(tk.nextToken());
                          System.out.println("tipo  "+tipo);
                                
                          if (tk.countTokens()<1) {
                              //
                              if ("c".equals(tipo) || "C".equals(tipo)  ) {
                                  
                                  valor=" ";
                              }else{
                                  
                                  valor="0";
                              }
                          }else{
                              
                              valor= (tk.nextToken());
                          }
                             
                           
                          //agrega en el array list de instrucciones
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(valor, posi, 4);// guarda en la tabla el valor de memoria
                            
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                            
                          Object nuevo[]=new Object[5];
                          nuevo[0]="";
                          nuevo[1]=pre;
                          nuevo[3]=variablenueva;
                          switch (tipo){
                              case "i":
                                  //
                                  case "I":
                                      //
                                      tipo="ENTERO";
                                      break;
                                
                                  case "r":
                                      //
                                      case "R":
                                          //
                                          tipo="REAL";
                                          break;
                                            
                                  case "c":
                                      
                                      case "C":
                                          //
                                          tipo="CADENA";
                                          break;
                          default:
                              //
                              errores= errores + "*parece error en el argumento tipo de variable en NUEVA\n"
                                            + "debe ser '(i)' para enteros '(r)' para reales\n"
                                            + "o '(c)' para caracteres";
                              System.out.println("entro");
                              throw new Exception("Invalid entry");
                                    
                                    
                          }
                          nuevo[2]=tipo;
                          nuevo[4]=valor;
                          var.add(nuevo); //almacena en laun array list para luego pasarlo a la  tabla variables 
                            
                          nvariables.add(variablenueva) ;
                          nvariables.add(valor) ;
                          break; 
                      }else{
                          errores=errores+"NUEVA debe tener tres o cuatro argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                                  
                  case "etiqueta":
                      
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==2) {
                          
                          //hace el segundo token de la linea
                          nombreetiqueta =(tk.nextToken());
                          numerolinea=(tk.nextToken());
                            
                            
                          inicialetiquetas++;
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(nombreetiqueta, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; //guarda en el vector principal de memoria
                            
                          Object netiqueta[]=new Object[4];
                            
                          //carga de nuevo el documento para recorrerlo de nuevo y encontrar la linea a etiquetar
                          FileReader file3 = new FileReader(url);
                          BufferedReader leer3 = new BufferedReader(file3);
                          String eti="";
                          int j;
                          //recorre el documento hasta la linea requerida 
                          for ( j = 0; j < Integer.parseInt(numerolinea); j++) {
                              //
                              eti=leer3.readLine();
                          }
                            
                          netiqueta[0]=pivote+j-1;//posicion en memoria
                          netiqueta[1]=pre; //programa al q pertenece
                          netiqueta[2]=nombreetiqueta;// nombre etiqueta mas
                          netiqueta[3]=eti; // la linea renombrada
                          etiq.add(netiqueta); // adiciona el arreglo a la tabla etiquetas
                          break;
                      }else{
                          errores=errores+"ETIQUETA debe tener tres argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                        
                  case "lea":
                      
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variablelea=(tk.nextToken());
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(variablelea, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          break;
                      }else{
                          errores=errores+"LEA debe tener tres argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                        
                  case "sume":
                      
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variablesume=(tk.nextToken());
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(variablesume, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          break;
                      }else{
                          errores=errores+"SUME debe tener dos argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                            
                  case "reste":
                      
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variablereste=(tk.nextToken());
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(variablereste, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          break;
                      }else{
                          errores=errores+"RESTE debe tener dos argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                            
                  case "multiplique":
                      
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variablemultiplique=(tk.nextToken());
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(variablemultiplique, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          break;
                      }else{
                          errores=errores+"MULTIPLIQUE debe tener dos argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                       
                  case "divida":
                      
                      //verifica  si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          //
                          variabledivida=(tk.nextToken());
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(variabledivida, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          break;
                      }else{
                          errores=errores+"DIVIDA debe tener dos argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                            
                  case "potencia":
                      //
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          //
                          variablepotencia=(tk.nextToken());
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(variablepotencia, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          break;
                      }else{
                          errores=errores+"POTENCIA debe tener dos argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                  //      
                  case "modulo":
                      
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          //
                          variablemodulo=(tk.nextToken());
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(variablemodulo, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          break;
                      }else{
                          errores=errores+"MODULO debe tener dos argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                            
                  case "concatene":
                     
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variableconcatene=(tk.nextToken());
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(variableconcatene, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          break;
                      }else{
                          errores=errores+"CONCATENE debe tener dos argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                            
                  case "elimine":
                      
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variableelimine=(tk.nextToken());
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(variableelimine, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          break;
                      }else{
                          errores=errores+"ELIMINE debe tener dos   argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                        
                  case "extraiga":
                      
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variableextraiga=(tk.nextToken());
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(variableextraiga, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          break;
                      }else{
                          errores=errores+"EXTRAIGA debe tener dos   argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                            
                  case "muestre":
                      
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                          
                          variablemuestre=(tk.nextToken());
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt(variablemuestre, posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          break;
                      }else{
                          errores=errores+"MUESTRE debe tener dos   argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                            
                  case "imprima":
                      
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==1) {
                             variableimprimir=(tk.nextToken());
                             //agrega la linea completa al mapa de memoria
                            modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                            modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                            modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                            modelo.setValueAt(variableimprimir, posi, 4);// guarda en la tabla el valor de memoria
                            memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                            break;
                      }else{
                          errores=errores+"IMPRIMA debe tener dos argumentos en esta linea";
                          throw new Exception("Invalid entry");
                      }
                            
                  case "retorne":
                      
                      //verifica si esta bn el formato sino salta el error
                      if (tk.countTokens()==0 || tk.countTokens()==1) {
                          
                          //agrega la linea completa al mapa de memoria
                          modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                          modelo.setValueAt(operacion, posi, 2);// guarda en la tabla  la instruccion del programa
                          modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                          modelo.setValueAt("----", posi, 4);// guarda en la tabla el valor de memoria
                          memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                          break;
                      }else{
                          errores=errores+"RETORNE debe tener uno argumento o dos, y este debe ser 0 en esta linea";
                          throw new Exception("Invalid entry");
                      }
                            
                   case "//":
                       
                       //agrega la linea completa al mapa de memoria
                       modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                       modelo.setValueAt("COMENTARIO", posi, 2);// guarda en la tabla  la instruccion del programa
                       modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                       modelo.setValueAt("----", posi, 4);// guarda en la tabla el valor de memoria
                       memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                       break;
                        
                   case " ":
                       //agrega la linea completa al mapa de memoria
                       modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
                       modelo.setValueAt("LINEA VACIA", posi, 2);// guarda en la tabla  la instruccion del programa
                       modelo.setValueAt(linea, posi, 3);// guarda en la tabla el argumento de memoria
                       modelo.setValueAt("----", posi, 4);// guarda en la tabla el valor de memoria
                       memoriaprin[posi]=linea; // guarda en el vector principal de memoria
                       break;
                            
                   default:
                       //borra el ultimo programa cargado de la memoria 
                       for (int h = inicialmemoria; h < (int)memoria.getValue(); h++) {
                           //
                           modelo.setValueAt("", h, 1);
                           memoriaprin[h]="";
                       }
                       //hace el llamado a la exeption si algo esta mal en el archivo
                       errores=errores+"esta linea no cumple con ninguna de las reglas de sintaxis de .ch";
                       throw new Exception("Invalid entry");
              }
          }
          //carga si no hay problema las variables a la tabla variables
          for (int b = 0; b < var.size(); b++) {
                
              tvariables.addRow(var.get(b));
                
          }
            
          //ciclo que le asigna el valor de la posicion de memoria donde esta
          //ubicado y lo muestra en tabla de variables
          int tempoposi=posi;
          //toma el valor de las variables del nuevo archivo mas las filas ocupadas de la tabla
          int lim=(nvariables.size()/2)+q;
          for ( int r=q; r < lim; r++) {
              
              tempoposi++;
              //le da el valor de la posicion  de la variable en la memoria a  la tabala d evariables
              tvariables.setValueAt(tempoposi, r, 0);
          }
          //concatena el prefijo con la instruccion en el mapa de memoria las variables defidas
          for (int a = 0; a < nvariables.size(); a+=2) {
              
              posi++;
              //agrega toda la instruccion a la memoria 
               
              modelo.setValueAt(pre, posi, 1);// guarda en la tabla  de memoria el numero del programa
              modelo.setValueAt("VARIABLE", posi, 2);// guarda en la tabla  la instruccion del programa
              modelo.setValueAt(nvariables.get(a), posi, 3);// guarda en la tabla el argumento de memoria
              modelo.setValueAt(nvariables.get(a+1), posi, 4);// guarda en la tabla el valor de memoria
              memoriaprin[posi]=nvariables.get(a); // guarda en el vector principal de memoria
          }
          
          for (int c = 0; c < etiq.size(); c++) {
              //
              tetiquetas.addRow(etiq.get(c));
          }
          
          // se encarga de crear el contenido de un programa en la tabla de procesos
          Object []objectprocesos = new Object[8];
          objectprocesos[0]=pre;// # instancias
          objectprocesos[1]=nombre; // nombre del programa
          objectprocesos[2]=lNumeroLineas; // numero  de lineas del programa
          objectprocesos[3]=pivote; // rb
           
          objectprocesos[4]=  pivote+lNumeroLineas-1;     //posi; //registro limite de el programa
          objectprocesos[5]=posi;      //pivote ; // crea el rlp
          objectprocesos[6]=prioridad;      // ASIGNA LA PRIORIDAD
          float tllegada=0;
           
           
          //calculo tiempo de llegada
          if (llegada==1) {
              llegada=0;
          }else{
              
              int cantidadfilas=tprocesos.getRowCount();
              float tanterior=Float.parseFloat(tprocesos.getValueAt(cantidadfilas-1, 7).toString());
                 
              tllegada=(tanterior +lNumeroLineas)/4;
          }
          objectprocesos[7]=tllegada;      // ASIGNA LA PRIORIDAD
          tprocesos.addRow(objectprocesos);// adiciona a la tabla 
          pivote=posi+1;// crea el nuevo pivote
           
           
      }catch(Exception e){
          // retrocede el ide del programa en 1  pues el programa que lo ocupava no se cargo
          programa--;
          // recupera el valor de la memoria restante 
          int memoriarestante=((int)memoria.getValue()- pivote);
          // condicion que  define que tipo de error surgio en el proceso
          if (lNumeroLineas>memoriarestante) {
              // borra lo que se halla subido a la memoria si por casulaidad salta un error
              int tamaño=tabla2.getRowCount();
              int posisi=(int) tabla2.getValueAt(tamaño-1, 5);
              for (int i = posisi+1; i < (int)memoria.getValue(); i++) {
                  //
                  modelo.setValueAt("", i, 1);
                  modelo.setValueAt("", i, 2);
                  modelo.setValueAt("", i, 3);
                  modelo.setValueAt("", i, 4);
                 
              }
          //Messaje que se muestra cuando hay error dentro del 'try'
          JOptionPane.showMessageDialog(null, "Se generó un error al cargar el archivo \n"
                                          +"pues el tamaño de este es superior a la memoria restante");
          }else{
              //
              int tamaño=tabla2.getRowCount();
              int posisi=(int) tabla2.getValueAt(tamaño-1, 5);
              for (int i = posisi+1; i < (int)memoria.getValue(); i++) {
                  //
                  modelo.setValueAt("", i, 1);
                  modelo.setValueAt("", i, 2);
                  modelo.setValueAt("", i, 3);
                  modelo.setValueAt("", i, 4);
              }
              //Messaje que se muestra cuando hay error dentro del 'try'
              JOptionPane.showMessageDialog(null, "TENEMOS UN INCONVENIENTE:\n"
                                              + "Se generó un error al cargar el archivo en la\n"
                                              + "linea "+lexa+" es posible que uno de los datos\n"
                                              + "del archivo no coincida con el formato\n\n"
                                              + errores);
          }
      }   
  }
  
  //funcion encargada de tomar el valor de una variable y asignarselo a el acumulador 
  public void cargue(String programa, String variable){
       int tamaño=tvariables.getRowCount();
       int filas=0;
       //recorre la tabla de variable en busca de la condicion
       while(filas<tamaño){
           //captura las variables y las caste a a cadenas
           String prog=(String) tvariables.getValueAt(filas, 1);
           String vari=(String) tvariables.getValueAt(filas, 3);
           if (prog.equals(programa) && vari.equals(variable)) {
               //agrega toda la instruccion a la memoria  en el acumulador
               modelo.setValueAt(tvariables.getValueAt(filas, 4), 0, 4);
               filas=tamaño;
           }
           filas++;
       }
  }
  
  //Funcion encargada de recorrer la tabla de variables 
  //y almacenar el valor del acumulador en una variable dada 
  public void almacene (String programa, String variable){
      String acumu = (String) modelo.getValueAt(0,4).toString();
      float acumulador =Float.parseFloat(acumu);
      int filas=0;
      int tamaño=tvariables.getRowCount();
      // recorre la tabla de variable en busca de la condicion
      while(filas<tamaño){
          
          String prog=(String) tvariables.getValueAt(filas, 1);
          String vari=(String) tvariables.getValueAt(filas, 3);
          if (prog.equals(programa) && vari.equals(variable)) {
              
              int posicion= (int) tvariables.getValueAt(filas, 0);
              // agrega toda la instruccion a la memoria  en el acumulador 
              modelo.setValueAt(acumulador, posicion, 4);
              // agrega el nuevo valor a la tabla de variables
              tvariables.setValueAt(acumulador, filas, 4);
               
              filas=tamaño;
          }
          filas++;
      }
  }
  
  //funcion que crea un ciclo y retorna la posicion de memoria 
  //donde debe continuar  esta funcion se aplica para vaya  y vayasi
  public String vaya(String programa, String etiqueta ){
      
      int filas=0;
      String pos="";
      int tamaño=tetiquetas.getRowCount();
      // recorre la tabla de variable en busca de la condicion
      while(filas<tamaño){
          
          String prog=(String)tetiquetas.getValueAt(filas, 1);
          String etique=(String) tetiquetas.getValueAt(filas,2 );
          if (prog.equals(programa) && etique.equals(etiqueta)) {
              //
              // optiene el valor de la posicion donde debe iniciar el ciclo
              pos  = (String) tetiquetas.getValueAt(filas, 0).toString();
              filas=tamaño;
          }
      filas++;
      }
  return pos;
  }
  
  //funcion que le pide al usuario que ingrese un valor requerido
  //y lo retorna 
  public void lea(String programa, String variable){
      
      int filas=0;
      int tamaño=tvariables.getRowCount();
      String tipo="";
      int posicion=0,fil=0;
      //recorre la tabla de variable en busca de la condicion
      while(filas<tamaño){
          
          String prog=(String) tvariables.getValueAt(filas, 1);
          String vari=(String) tvariables.getValueAt(filas, 3);
          if (prog.equals(programa) && vari.equals(variable)) {
              
              // captura el tipo de la variable con la cual se busca  captuara un dato
              tipo=  (String) tvariables.getValueAt(filas, 2);
              posicion= (int) tvariables.getValueAt(filas, 0);
              fil=filas;
              filas=tamaño;
          }
      filas++;
      }
      //solicita el dato al usuario
      String datodeusuario=JOptionPane.showInputDialog("INGRESE UN VALOR DE TIPO "+tipo); 
      
      //agrega el nuevo valor a la memoria  
      modelo.setValueAt(datodeusuario, posicion, 4);
      //agrega el nuevo valor a la tabla de variables
      tvariables.setValueAt(datodeusuario, fil, 4);
  }
  
  //funcion  que suma el valor del acumulador con el valor de una variable 
  public void sume(String programa, String variable){
      String acumu = (String) modelo.getValueAt(0,4).toString();
      float acumulador =Float.parseFloat(acumu);
      int filas=0;
      int tamaño=tvariables.getRowCount();
       // recorre la tabla de variable en busca de la condicion
       while(filas<tamaño){
           String prog=(String) tvariables.getValueAt(filas, 1);
           String vari=(String) tvariables.getValueAt(filas, 3);
           if (prog.equals(programa) && vari.equals(variable)) {
              // captura la posicion de memoria dond eesta la variable
              String val=String.valueOf(tvariables.getValueAt(filas, 4));
              float valor=  Float.parseFloat(val);
               
              float resultado=acumulador + valor;
              // agrega el nuevo valor  a la memoria  en el acumulador 
              modelo.setValueAt(resultado, 0, 4);
              filas=tamaño;
           }
           filas++;
       }
  }
  
  //funcion que reste el valor del acumulador con el valor de una variable
  public void reste(String programa, String variable){
      String acumu = (String) modelo.getValueAt(0,4).toString();
      float acumulador =Float.parseFloat(acumu);
      int filas=0;
      int tamaño=tvariables.getRowCount();
      //recorre la tabla de variable en busca de la condicion
      while(filas<tamaño){
          
          String prog=(String) tvariables.getValueAt(filas, 1);
          String vari=(String) tvariables.getValueAt(filas, 3);
          if (prog.equals(programa) && vari.equals(variable)) {
              // captura la posicion de memoria dond eesta la variable
              int posicion= (int) tvariables.getValueAt(filas, 0);
              String val=(String) tvariables.getValueAt(filas, 4);
              float valor=  Float.parseFloat(val);
               
              float resultado=acumulador - valor;
              // agrega el nuevo valor  a la memoria  en el acumulador 
              modelo.setValueAt(resultado, 0, 4);
              filas=tamaño;
          }
      filas++;
      }
  }
  
  //funcion que multiplique el valor del acumulador con el valor de una variable
  public void multiplique(String programa, String variable){
      String acumu = (String) modelo.getValueAt(0,4).toString();
      float acumulador =Float.parseFloat(acumu);
      int filas=0;
      int tamaño=tvariables.getRowCount();
      // recorre la tabla de variable en busca de la condicion
      while(filas<tamaño){
          
          String prog=(String) tvariables.getValueAt(filas, 1);
          String vari=(String) tvariables.getValueAt(filas, 3);
          if (prog.equals(programa) && vari.equals(variable)) {
              // captura la posicion de memoria dond eesta la variable
              String val=String.valueOf(tvariables.getValueAt(filas, 4));
              float valor=  Float.parseFloat(val);
               
              float resultado=acumulador * valor;
              // agrega el nuevo valor  a la memoria  en el acumulador 
              modelo.setValueAt(resultado, 0, 4);
              filas=tamaño;
          }
          filas++;
      }
  }
  
  //funcion que divide el valor del acumulador con el valor de una variable
  public void divide(String programa, String variable){
      
      String acumu = (String) modelo.getValueAt(0,4).toString();
      float acumulador =Float.parseFloat(acumu);
      int filas=0;
      int tamaño=tvariables.getRowCount();
      // recorre la tabla de variable en busca de la condicion
      while(filas<tamaño){
          
          String prog=(String) tvariables.getValueAt(filas, 1);
          String vari=(String) tvariables.getValueAt(filas, 3);
          if (prog.equals(programa) && vari.equals(variable)) {
              
              // captura la posicion de memoria dond eesta la variable
              String val=String.valueOf(tvariables.getValueAt(filas, 4));
              float valor=  Float.parseFloat(val);
              //EN CASO TAL DE QUE LA VARIABLE TENGA UN CERO  VERIFICA  PRIMERO
              if(valor!=0){
                  //
                  float resultado=acumulador / valor;
                  // agrega el nuevo valor  a la memoria  en el acumulador 
                  modelo.setValueAt(resultado, 0, 4);
                
              }else{
                  JOptionPane.showOptionDialog(this, "HAY DIVICION CON CERO POR TANTO"
                           + "EL ACUMULADOR CONCERVA SU VALOR ORIGINAL", "ALERTA X/0",
                           JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{" OK "},"OK");
              }filas=tamaño;
               
          }
      filas++;
      }
  }
  
  //funcion  que potencia el valor del acumulador con el valor de una variable
  public void potencia(String programa, String variable){
      String acumu = (String) modelo.getValueAt(0,4).toString();
      float acumulador =Float.parseFloat(acumu);
      int filas=0;
      int tamaño=tvariables.getRowCount();
      // recorre la tabla de variable en busca de la condicion
      while(filas<tamaño){
          //
          String prog=(String) tvariables.getValueAt(filas, 1);
          String vari=(String) tvariables.getValueAt(filas, 3);
          if (prog.equals(programa) && vari.equals(variable)) {
              // captura la posicion de memoria dond eesta la variable
              String val=String.valueOf(tvariables.getValueAt(filas, 4));
              float valor=  Float.parseFloat(val);
               
              float resultado = (float) Math.pow(acumulador, valor);
              // agrega el nuevo valor  a la memoria  en el acumulador 
              modelo.setValueAt(resultado, 0, 4);
              filas=tamaño;
          }
          filas++;
      }
      
  }
  
  //funcion de modulo el valor del acumulador con el valor de una variable
  public void modulo(String programa, String variable){
      String acumu = (String) modelo.getValueAt(0,4).toString();
      float acumulador =Float.parseFloat(acumu);
      int filas=0;
      int tamaño=tvariables.getRowCount();
      // recorre la tabla de variable en busca de la condicion
      while(filas<tamaño){
          
          String prog=(String) tvariables.getValueAt(filas, 1);
          String vari=(String) tvariables.getValueAt(filas, 3);
          if (prog.equals(programa) && vari.equals(variable)) {
              
              // captura la posicion de memoria dond esta la variable
              String val=String.valueOf(tvariables.getValueAt(filas, 4));
              float valor=  Float.parseFloat(val);
              //EN CASO TAL DE QUE LA VARIABLE TENGA UN CERO  VERIFICA  PRIMERO
              if(valor!=0){
                  
                  float resultado=acumulador % valor;
                  // agrega el nuevo valor  a la memoria  en el acumulador 
                  modelo.setValueAt(resultado, 0, 4);
                
              }else{
                  
                  JOptionPane.showOptionDialog(this, "HAY DIVICION CON CERO POR TANTO"
                           + "EL ACUMULADOR CONCERVA SU VALOR ORIGINAL", "ALERTA X/0",
                           JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{" OK "},"OK");
               }filas=tamaño;}
           filas++;
       }
  }
  
  //funcion de concatenar  el  valor del acumulador con el valor de una variable
  public void concatene(String programa, String variable){
      String acumulador=  (String) modelo.getValueAt(0, 4).toString();
      int filas=0;
      int tamaño=tvariables.getRowCount();
      // recorre la tabla de variable en busca de la condicion
      while(filas<tamaño){
          //
          String prog=(String) tvariables.getValueAt(filas, 1);
          String vari=(String) tvariables.getValueAt(filas, 3);
          if (prog.equals(programa) && vari.equals(variable)) {
              // captura la posicion de memoria dond eesta la variable
              int posicion= (int) tvariables.getValueAt(filas, 0);
               
               
              // hace la concatenacion del  acumulador y el valor de la variable
              String resultado = tvariables.getValueAt(filas, 4)+acumulador ;
              // agrega el nuevo valor  a la memoria  en el acumulador 
              modelo.setValueAt(resultado, posicion, 4);
              tvariables.setValueAt(resultado, filas, 4);
               
              filas=tamaño;
          }     
          filas++;
      }
  }
  
  //funcion que elimina una parte del acumulador con el valor de una variable
  public void elimine(String programa, String variable){
      String acumulador=  (String) tabla.getValueAt(0, 4),resultado="";
      int filas=0;
      int tamaño=tvariables.getRowCount();
      // recorre la tabla de variable en busca de la condicion
      while(filas<tamaño){
          //
          String prog=(String) tvariables.getValueAt(filas, 1);
          String vari=(String) tvariables.getValueAt(filas, 3);
          if (prog.equals(programa) && vari.equals(variable)) {
              // captura la posicion de memoria dond eesta la variable
              int posicion= (int) tvariables.getValueAt(filas, 0);
              String valor=   (String) tvariables.getValueAt(filas, 4);
              // hace la eliminacion de una parte del   acumulador con el valor de valor de la variable
              resultado = acumulador.replace(valor, "");
              // agrega el nuevo valor  a la memoria  en el acumulador 
              modelo.setValueAt(resultado, posicion, 4);
              tvariables.setValueAt(resultado, filas, 4);
               
              filas=tamaño;
          }
      filas++;
      }
  }
  
  //funcion que extraiga los primeros caracteres del acumulador deacuerdo con el valor de una variable
  public void extraiga(String programa, String variable){
      String acumulador=  (String) tabla.getValueAt(0, 4),resultado="";
      int filas=0;
      int tamaño=tvariables.getRowCount();
      // recorre la tabla de variable en busca de la condicion
      while(filas<tamaño){
          
          String prog=(String) tvariables.getValueAt(filas, 1);
          String vari=(String) tvariables.getValueAt(filas, 3);
          if (prog.equals(programa) && vari.equals(variable)) {
              
              // captura la posicion de memoria dond eesta la variable
              int posicion= (int) tvariables.getValueAt(filas, 0);
              int valor=    (int) tvariables.getValueAt(filas, 4);
              // hace la extraccion de una parte del   acumulador con el valor de  la variable
              resultado = acumulador.substring(0, valor);
              // agrega el nuevo valor  a la memoria  en el acumulador 
              modelo.setValueAt(resultado, posicion, 4);
              tvariables.setValueAt(resultado, filas, 4);
               
              filas=tamaño;
          }
      filas++;
      }
  }
  
  //funcion mostrar en el monitor los primeros caracteres del acumulador de acuerdo con el valor de una variable
  public void mostrar(String programa, String variable){
      String resultado="";
      int filas=0;
      int tamaño=tvariables.getRowCount();
      // recorre la tabla de variable en busca de la condicion
       while(filas<tamaño){
           String prog=(String) tvariables.getValueAt(filas, 1);
           String vari=(String) tvariables.getValueAt(filas, 3);
           if (prog.equals(programa) && vari.equals(variable)) {
           
              // captura la posicion de memoria dond eesta la variable
              
               String valor=    (String) tvariables.getValueAt(filas, 4).toString();
               // hace la extraccion de una parte del   acumulador con el valor de  la variable
               resultado = valor;
                
               
               String muestra=monitor.getText();
               muestra=muestra+"RESULTADO DEL PROGRAMA "+ programa+".ch\nMOSTRANDO VALOR DE LA VARIABLE "+variable+" = "+resultado+"\n\n";
               monitor.setText(muestra);
               
                break;
           }
           filas++;
       }
  }
  
  //funcion   mostrar en el impresora los primeros caracteres del acumulador deacuerdo con el valor de una variable
  public void imprimir(String programa, String variable){
      String resultado="";
      int filas=0;
      int tamaño=tvariables.getRowCount();
      // recorre la tabla de variable en busca de la condicion
      while(filas<tamaño){
          
          String prog=(String) tvariables.getValueAt(filas, 1);
          String vari=(String) tvariables.getValueAt(filas, 3);
          if (prog.equals(programa) && vari.equals(variable)) {
              
              // captura la posicion de memoria dond eesta la variable
               
              String valor=    (String) tvariables.getValueAt(filas, 4).toString();
              // hace la extraccion de una parte del   acumulador con el valor de  la variable
              resultado = valor;
              String muestra=impresora.getText();
              muestra=muestra+"RESULTADO DEL PROGRAMA "+ programa +".ch\nMOSTRANDO VALOR DE LA VARIABLE "+variable+" = "+resultado+"\n\r\n\r";
               
              impresora.setText(muestra);
              
               
              break;
          }
      filas++;
      }
  }
  
  public void ejecutar(){
      // toma el valor de el kernel para iniciar a ejecutar
      int inicio=(int) kernel.getValue();
      // define el limite  de las  instrucciones en memoria
      int ultimaf= tabla2.getRowCount();
      
      int limite= (int) tabla2.getValueAt(ultimaf-1, 5);
      
      
      
      //ciclo encargado de recorrer todas las instrucciones en la memoria
      for (int i = inicio+1; i <= limite; i++) {
        
          // variables capturadoras de cada fila de la tabla de memoria
          String pos_memoria=  (String) modelo.getValueAt(i, 0).toString();
          String programaa=  (String) modelo.getValueAt(i, 1).toString();
          String instruccion=  (String) modelo.getValueAt(i, 2).toString();
          String argumento=  (String) modelo.getValueAt(i, 3).toString();
          String valor=  (String) modelo.getValueAt(i, 4).toString();
         
          macumulador.setText(modelo.getValueAt(0, 4).toString());
          mpos_mem.setText(pos_memoria);
          minst.setText(argumento);
          mvalor.setText(valor);

          switch (instruccion) {
              //
              case "cargue":
                  //
                  cargue( programaa, valor);
              break;
                        
              case "almacene":
                  //
                  almacene (programaa, valor);
              break;
                            
              case "vaya":
                  
                  vaya(programaa, valor );
                            
              break;
                        
              case "vayasi":
                  StringTokenizer etiquetas = new StringTokenizer(valor, ";");
                  String inicioo = etiquetas.nextToken();
                  String fin = etiquetas.nextToken();
                  String continua= String.valueOf(i);
                  float acum =(float) modelo.getValueAt(0,4);
                  if (acum>0.0) {
                      
                      continua=vaya(programaa, inicioo );
                      i=Integer.parseInt(continua)-1;
                               
                  }
                  else if(acum<0.0){
                      //
                      continua=vaya(programaa, fin );
                      i=Integer.parseInt(continua)-1;
                  }
                  break;   
                            
              case "lea":
                  
                  lea( programaa, valor);
                  break;
                        
              case "sume":
                  
                  sume(programaa,valor);
                  break;
                            
              case "reste":
                  
                  reste(programaa,valor);
                  break;
                            
              case "multiplique":
                            
                  multiplique(programaa,valor);          
                  break;
                         
              case "divida":
                            
                  divide(programaa,valor);
                  break;
                            
              case "potencia":
                            
                  potencia(programaa,valor);
                  break;
                                  
              case "modulo":
                            
                  modulo(programaa,valor);          
                  break;
                            
              case "concatene":
                            
                  concatene(programaa,valor);
                  break;
                                   
              case "elimine":
                            
                  elimine(programaa,valor);          
                  break;
                               
              case "extraiga":
                            
                  extraiga(programaa,valor);
                  break;
                            
              case "muestre":
                           
                  mostrar(programaa,valor);
                  break;
                                      
              case "imprima":
                            
                  imprimir(programaa,valor);
                  break;
                                      
              case "retorne":
                  
                  modelo.setValueAt(0, 0, 4);          
                  break;  
          } 
      } 
  }
  
  public void pasoapaso(){
      //toma el valor de el kernel para iniciar a ejecutar
      int inicio=(int) kernel.getValue();
      //define el limite de las  instrucciones en memoria
      int ultimaf= tabla2.getRowCount();
     
      int limite= (int) tabla2.getValueAt(ultimaf-1, 5);
      int i = inicio+1;
      //ciclo encargado de recorrer todas las instrucciones en la memoria
      while ( i < limite) {
          
          //variables capturadoras de cada fila de la tabla de memoria
          String pos_memoria=  (String) modelo.getValueAt(i, 0).toString();
          String programaa=  (String) modelo.getValueAt(i, 1).toString();
          String instruccion=  (String) modelo.getValueAt(i, 2).toString();
          String argumento=  (String) modelo.getValueAt(i, 3).toString();
          String valor=  (String) modelo.getValueAt(i, 4).toString();
          
          //muestra en la interfaz los procesos que se estan ejecutando
          macumulador.setText(modelo.getValueAt(0, 4).toString());
          mpos_mem.setText(pos_memoria);
          minst.setText(argumento);
          mvalor.setText(valor);
          
            
          
          switch (instruccion) {
              //
              case "cargue":
                  
                  cargue( programaa, valor);
                  break;
                        
              case "almacene":
                  
                  almacene (programaa, valor);
                  break;
                            
              case "vaya":
                  
                  vaya(programaa, valor );
                  break;
                        
              case "vayasi":
                  
                  StringTokenizer etiquetas = new StringTokenizer(valor, ";");
                  String inicioo = etiquetas.nextToken();
                  String fin = etiquetas.nextToken();
                  String continua= String.valueOf(i);
                  float acum =(float) modelo.getValueAt(0,4);
                  
                  if (acum>0.0) {
                       
                      continua=vaya(programaa, inicioo );
                      i=Integer.parseInt(continua)-1;
                               
                  }
                  else if(acum<0.0){
                      
                      continua=vaya(programaa, fin );
                      i=Integer.parseInt(continua)-1;
                                }
                  break;   
                            
              case "lea":
                            
                  lea( programaa, valor);
                  break;
                                  
              case "sume":
                  
                  sume(programaa,valor);
                  break;
                            
              case "reste":
                  
                  reste(programaa,valor);
                  break;
                            
              case "multiplique":
                            
                  multiplique(programaa,valor);
                  break;
                         
              case "divida":
                  //
                  divide(programaa,valor);
                  break;
                            
              case "potencia":
                            
                  potencia(programaa,valor);
                  break;
                            
              case "modulo":
                  //
                  modulo(programaa,valor);
                  break;
                            
              case "concatene":
                            
                  concatene(programaa,valor);
                  break;
                            
              case "elimine":
                            
                  elimine(programaa,valor);
                  break;
                                  
              case "extraiga":
                            
                  extraiga(programaa,valor);
                  break;
                            
              case "muestre":
                           
                  mostrar(programaa,valor);
                  break;
                            
              case "imprima":
                            
                  imprimir(programaa,valor);
                  break;
                            
              case "retorne":
                             
                  modelo.setValueAt(0, 0, 4);          
                  break;
          }
          if(JOptionPane.showOptionDialog(this, "¿DESEA SEGUIR LA EJECUCION PASO A PASO?", "Mensaje de Alerta",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{" SI "," NO "},"NO")==0)
          {
              i++;
          }  else{
          ejecutar();
          String muestra=monitor.getText();
          for (int j = 0; j < resultados.size(); j++) {
              
              muestra=muestra+resultados.get(j)[1];
          }
          
          monitor.setText(muestra);
          impresora.setText(muestra);
          i=limite;
          }
      }
   }
  
  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu5 = new javax.swing.JMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        monitor = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        impresora = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        kernel = new javax.swing.JSpinner();
        memoria = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        total_memoria = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        estado = new javax.swing.JLabel();
        encender = new javax.swing.JButton();
        apagarmaquina1 = new javax.swing.JToggleButton();
        ejecutar = new javax.swing.JButton();
        pasoapaso = new javax.swing.JButton();
        editor = new javax.swing.JButton();
        botoncargar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabla2 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablavariables = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaetiquetas = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        macumulador = new javax.swing.JLabel();
        mpos_mem = new javax.swing.JLabel();
        minst = new javax.swing.JLabel();
        mvalor = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        encender2 = new javax.swing.JMenuItem();
        cargarprograma = new javax.swing.JMenuItem();
        apagarmaquina2 = new javax.swing.JMenuItem();
        cerrar = new javax.swing.JMenuItem();
        EJEC = new javax.swing.JMenu();
        encender3 = new javax.swing.JMenuItem();
        encender4 = new javax.swing.JMenuItem();
        IMP = new javax.swing.JMenu();
        impri = new javax.swing.JMenuItem();
        AYUDA = new javax.swing.JMenu();
        documentacion = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        acercade = new javax.swing.JMenuItem();

        jMenu5.setText("jMenu5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(monitor);

        jScrollPane2.setViewportView(impresora);

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.setEnabled(false);
        tabla.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tabla);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MAPA DE MEMORIA");

        kernel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kernel.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                kernelStateChanged(evt);
            }
        });

        memoria.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        memoria.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                memoriaStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("KERNEL");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("MEMORIA");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("total memoria a utilizar:");

        total_memoria.setBackground(new java.awt.Color(255, 255, 255));
        total_memoria.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        total_memoria.setForeground(new java.awt.Color(255, 255, 255));
        total_memoria.setText("29");
        total_memoria.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                total_memoriaAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("LA MAQUINA ESTA:");

        estado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        estado.setForeground(new java.awt.Color(255, 255, 255));
        estado.setText("APAGADA");

        encender.setText("Encender maquina");
        encender.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                encenderMouseClicked(evt);
            }
        });
        encender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encenderActionPerformed(evt);
            }
        });

        apagarmaquina1.setText("Apagar maquina ");
        apagarmaquina1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apagarmaquina1ActionPerformed(evt);
            }
        });

        ejecutar.setText("Ejecutar ");
        ejecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ejecutarActionPerformed(evt);
            }
        });

        pasoapaso.setText("Paso a paso ");
        pasoapaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasoapasoActionPerformed(evt);
            }
        });

        editor.setText("Editor");
        editor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorActionPerformed(evt);
            }
        });

        botoncargar.setText("Cargar archivo .ch");
        botoncargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botoncargarActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("ACUMULADOR");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("POS-MEM");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("INSTRUCCION");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("VALOR");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("tabla de procesos");

        tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Título 5", "Título 6"
            }
        ));
        tabla2.setEnabled(false);
        tabla2.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tabla2);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("tabla de variables");

        tablavariables.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "null", "Título 4", "null"
            }
        ));
        tablavariables.setEnabled(false);
        jScrollPane6.setViewportView(tablavariables);

        tablaetiquetas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Título 3"
            }
        ));
        tablaetiquetas.setEnabled(false);
        jScrollPane5.setViewportView(tablaetiquetas);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("tabla de etiquetas");

        macumulador.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        macumulador.setForeground(new java.awt.Color(255, 255, 255));
        macumulador.setText("   ");

        mpos_mem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        mpos_mem.setForeground(new java.awt.Color(255, 255, 255));
        mpos_mem.setText("   ");

        minst.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        minst.setForeground(new java.awt.Color(255, 255, 255));
        minst.setText("   ");

        mvalor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        mvalor.setForeground(new java.awt.Color(255, 255, 255));
        mvalor.setText("   ");

        jMenu1.setText("Archivo");

        encender2.setText("Encender  maquina");
        encender2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encender2ActionPerformed(evt);
            }
        });
        jMenu1.add(encender2);

        cargarprograma.setText("Cargar programa");
        cargarprograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarprogramaActionPerformed(evt);
            }
        });
        jMenu1.add(cargarprograma);

        apagarmaquina2.setText("Apagar maquina ");
        apagarmaquina2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apagarmaquina2ActionPerformed(evt);
            }
        });
        jMenu1.add(apagarmaquina2);

        cerrar.setText("Cerrar ");
        cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarActionPerformed(evt);
            }
        });
        jMenu1.add(cerrar);

        jMenuBar1.add(jMenu1);

        EJEC.setText("Ejecutar");

        encender3.setText("Recorrido");
        encender3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encender3ActionPerformed(evt);
            }
        });
        EJEC.add(encender3);

        encender4.setText("Paso a paso ");
        encender4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encender4ActionPerformed(evt);
            }
        });
        EJEC.add(encender4);

        jMenuBar1.add(EJEC);

        IMP.setText("Imprimir");

        impri.setText("Imprimir ");
        impri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                impriActionPerformed(evt);
            }
        });
        IMP.add(impri);

        jMenuBar1.add(IMP);

        AYUDA.setText("Ayuda");

        documentacion.setText("Documentacion");
        documentacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentacionActionPerformed(evt);
            }
        });
        AYUDA.add(documentacion);

        jMenuItem9.setText("Manual Tecnico ");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        AYUDA.add(jMenuItem9);

        jMenuItem10.setText("Manual Usuario ");
        AYUDA.add(jMenuItem10);

        acercade.setText("Acerca de CH-maquina");
        acercade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acercadeActionPerformed(evt);
            }
        });
        AYUDA.add(acercade);

        jMenuBar1.add(AYUDA);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(estado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(kernel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(memoria, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(73, 73, 73))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(botoncargar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(encender, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(apagarmaquina1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(ejecutar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editor, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pasoapaso)))
                        .addGap(97, 97, 97)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mpos_mem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(macumulador, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(minst, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mvalor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(total_memoria, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(114, 114, 114)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(252, 252, 252))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addComponent(jLabel5)
                .addGap(320, 320, 320)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(126, 126, 126))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(119, 119, 119)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80)
                                .addComponent(encender)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(apagarmaquina1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botoncargar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ejecutar)
                                    .addComponent(editor)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(kernel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel8)
                                        .addComponent(estado)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(memoria, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3))
                                        .addGap(13, 13, 13)
                                        .addComponent(total_memoria, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pasoapaso))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(macumulador))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(mpos_mem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(minst))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(mvalor))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void encender2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encender2ActionPerformed
        // TODO add your handling code here:
        
        //Hace el llamado a la funcion para que reproduzca el sonido de encendido
        encender();
    }//GEN-LAST:event_encender2ActionPerformed

    private void cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarActionPerformed
        // TODO add your handling code here:
        
        // codigo encargado de CERRAR EL PROGRAMA
        
        temporales();// borra los temporales
        if(JOptionPane.showOptionDialog(this, "¿ESTA SEGURO QUE DESEA SALIR DEL PROGRAMA?", "Mensaje de Alerta", 
          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{" SI "," NO "},"NO")==0)
        {
            son("cierre");
        
            // se encarga de  detener un instante el proceso
        try {
            Thread.sleep(3000);// el tiempo es en milisegundos
        } catch (InterruptedException ex) {

        }
        System.exit(0);
        }else{
            //
            JOptionPane.showMessageDialog(this, "PUEDE CONTINUAR CON LA EJECUCION DEL PROGRAMA");
        }
    }//GEN-LAST:event_cerrarActionPerformed

    private void encender3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encender3ActionPerformed
        // TODO add your handling code here:
        
        //boton ejecutar 
        
        botoncargar.setVisible(false);
        estado.setText("Modo Usuario");
        ejecutar();
    }//GEN-LAST:event_encender3ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void acercadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acercadeActionPerformed
        // TODO add your handling code here:
        
        JOptionPane.showOptionDialog(this, "Product Version: Mi CH-Maquina   V.1.0.0\n" +
                "Actualizaciones: en proceso...\n" +
                "Java: 1.7.0_51; Java HotSpot(TM) 64-Bit Server VM 24.51-b03\n" +
                "Runtime: Java(TM) SE Runtime Environment 1.7.0_51-b13\n" +
                "System recomendado: Windows 10 y posterior\n" +
                "creado por :Juan David Fajardo B -- JDFB\n" +
                "Universidad Nacional de Colombia - Sede Manizales\n"
                + "fecha inicio creacion :febrero 2024\n\n"
                + "simulador de OS encargado de leer instrucciones  de un archivo con \n"
                + "extencion .CH en este estan los pasos y valores iniciales que el \n"
                + "simulador debe interpretar y ejecutar, lo puede hacer de modo recorrido o \n"
                + "paso a paso, durante la ejecucion se ve el mapa de memoria y que hay \n"
                + "almacenado en ella ademas de las variables declaradas y procesos activos,\n"
                + "los resultados del proceso pueden visualizarce en monitor e impresion.\n\n"
                + "© feb2024 - mar2024 CH-Maquina All rights reserved.", "Acerca de mi CH-Maquina."
                       , 
        JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{" OK "},"OK");
   
    }//GEN-LAST:event_acercadeActionPerformed

    private void kernelStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_kernelStateChanged
        // en caso tal de que el kernel supere el tamaño de la memoria la memoria se modificara en 1 mas que el kernel
        int kertemp = (int) kernel.getValue();
        int memtemp = (int) memoria.getValue();
        if (memtemp <= kertemp) {
            kertemp=memtemp-1;
            kernel.setValue(kertemp);
        }
        //muestra la memoria disponible para los programas
        memtotal();

    }//GEN-LAST:event_kernelStateChanged

    private void memoriaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_memoriaStateChanged
        // TODO add your handling code here:
        // en caso tal de que el kernel supere  el tamaño de la memoria la memoria se modificara  en 1 mas que el kernel
        int kertemp = (int) kernel.getValue();
        int memtemp = (int) memoria.getValue();
        if (kertemp >= memtemp) {
            memtemp=kertemp+1;
            memoria.setValue(memtemp);
        }
        //muestra la memoria disponible para los programas
        memtotal();
    }//GEN-LAST:event_memoriaStateChanged

    private void total_memoriaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_total_memoriaAncestorAdded
        // TODO add your handling code here:

    }//GEN-LAST:event_total_memoriaAncestorAdded

    private void encenderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_encenderMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_encenderMouseClicked

    private void encenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encenderActionPerformed
        // HACE EL LLAMADO A LA FUNCION PARA QUE REPRODUSCA EL SONIDO DE ENSENDIDO
        encender();
    }//GEN-LAST:event_encenderActionPerformed

    private void apagarmaquina1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apagarmaquina1ActionPerformed
        // TODO add your handling code hermie:
        //llama la funcion encargada de apagar la maquina
        apagar();
    }//GEN-LAST:event_apagarmaquina1ActionPerformed

    private void ejecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ejecutarActionPerformed
        // TODO add your handling code here:
        botoncargar.setVisible(false);
        estado.setText("Modo Usuario");
        ejecutar();

    }//GEN-LAST:event_ejecutarActionPerformed

    private void pasoapasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasoapasoActionPerformed
        // TODO add your handling code here:
        botoncargar.setVisible(false);
        estado.setText("Modo Usuario");
        pasoapaso();
    }//GEN-LAST:event_pasoapasoActionPerformed

    private void editorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editorActionPerformed
        // TODO add your handling code here:
        // muestra  el boton cargar archivo de nuevo
        botoncargar.setVisible(true);
        // abre el panel del editor
        
        CHAmbiente des= new CHAmbiente();
        des.setVisible(true);

    }//GEN-LAST:event_editorActionPerformed

    private void botoncargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botoncargarActionPerformed
        // TODO add your handling code here:
        ejecutar.setVisible(true);
        IMP.setEnabled(true);
        EJEC.setEnabled(true);

        pasoapaso.setVisible(true);
        cargararchivo();
    }//GEN-LAST:event_botoncargarActionPerformed

    private void cargarprogramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarprogramaActionPerformed
        // TODO add your handling code here:
        
        cargararchivo();
    }//GEN-LAST:event_cargarprogramaActionPerformed

    private void apagarmaquina2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apagarmaquina2ActionPerformed
        // TODO add your handling code here:
        
        //llama la funcion encargada de apagar la maquina 
        apagar();
    }//GEN-LAST:event_apagarmaquina2ActionPerformed

    private void encender4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encender4ActionPerformed
        // TODO add your handling code here:
        botoncargar.setVisible(false);
        estado.setText("Modo Usuario");
        pasoapaso();
    }//GEN-LAST:event_encender4ActionPerformed

    private void documentacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentacionActionPerformed
        // TODO add your handling code here:
        
        // carga  el documento  que contiene la documentacion 
           
//        try{
//            //nuevo archivo en esa direccion
//            File temp = new File(arc);
//            InputStream is = this.getClass().getResourceAsStream("/documentacion/documentacion.pdf");
//            FileOutputStream archivoDestino = new FileOutputStream(temp);
//            //FileWriter fw = new FileWriter(temp);
//            byte[] buffer = new byte[1024*1024];
//            //lees el archivo hasta que se acabe...
//            int nbLectura;
//            while ((nbLectura = is.read(buffer)) != -1)
//                archivoDestino.write(buffer, 0, nbLectura);
//            //cierras el archivo,el inputS y el FileW
//            //fw.close();
//            archivoDestino.close();
//            is.close();
//            //abres el archivo temporal
//            Desktop.getDesktop().open(temp);
//            
//        } catch (IOException ex) {
//            
//        }   
    }//GEN-LAST:event_documentacionActionPerformed

    private void impriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_impriActionPerformed
        // TODO add your handling code here:
        try{
            
            impresora.print();
        
        }catch(PrinterException ex){
            
        }
    }//GEN-LAST:event_impriActionPerformed

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
            java.util.logging.Logger.getLogger(CHEntrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CHEntrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CHEntrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CHEntrada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CHEntrada().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu AYUDA;
    private javax.swing.JMenu EJEC;
    private javax.swing.JMenu IMP;
    private javax.swing.JMenuItem acercade;
    private javax.swing.JToggleButton apagarmaquina1;
    private javax.swing.JMenuItem apagarmaquina2;
    private javax.swing.JButton botoncargar;
    private javax.swing.JMenuItem cargarprograma;
    private javax.swing.JMenuItem cerrar;
    private javax.swing.JMenuItem documentacion;
    private javax.swing.JButton editor;
    private javax.swing.JButton ejecutar;
    private javax.swing.JButton encender;
    private javax.swing.JMenuItem encender2;
    private javax.swing.JMenuItem encender3;
    private javax.swing.JMenuItem encender4;
    private javax.swing.JLabel estado;
    private javax.swing.JTextPane impresora;
    private javax.swing.JMenuItem impri;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSpinner kernel;
    private javax.swing.JLabel macumulador;
    private javax.swing.JSpinner memoria;
    private javax.swing.JLabel minst;
    private javax.swing.JTextPane monitor;
    private javax.swing.JLabel mpos_mem;
    private javax.swing.JLabel mvalor;
    private javax.swing.JButton pasoapaso;
    private javax.swing.JTable tabla;
    private javax.swing.JTable tabla2;
    private javax.swing.JTable tablaetiquetas;
    private javax.swing.JTable tablavariables;
    private javax.swing.JLabel total_memoria;
    // End of variables declaration//GEN-END:variables

}
