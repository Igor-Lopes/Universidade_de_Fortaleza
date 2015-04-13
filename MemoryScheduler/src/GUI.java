
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Igor Freitas Universidade de Fortaleza (Matrícula: 1110546/7)
 * Sistemas Operacionais @Prof Eriko
 *
 */
public class GUI extends javax.swing.JFrame {

    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonHelp;
    private javax.swing.JButton jButtonNewProcess;
    private javax.swing.JButton jButtonStart;
    private javax.swing.JButton jButtonStop;
    private javax.swing.JComboBox jComboBox;
    private javax.swing.JFormattedTextField jFormattedTextFieldMemorySize;
    private javax.swing.JFormattedTextField jFormattedTextFieldProcesses;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelAbortedProcessesJpanel;
    private javax.swing.JLabel jLabelAlgorithm;
    private javax.swing.JLabel jLabelBlocks;
    private javax.swing.JLabel jLabelBlocksOutput;
    private javax.swing.JLabel jLabelFree;
    private javax.swing.JLabel jLabelFreeOutput;
    private javax.swing.JLabel jLabelInUse;
    private javax.swing.JLabel jLabelInUseOutPut;
    private javax.swing.JLabel jLabelMemoryBlocksJPanel;
    private javax.swing.JLabel jLabelMemoryOutPut;
    private javax.swing.JLabel jLabelMemorySize;
    private javax.swing.JLabel jLabelProcesses;
    private javax.swing.JLabel jLabelTotalAbortedProcesses;
    private javax.swing.JLabel jMemory;
    private javax.swing.JPanel jPanelMemory;
    private javax.swing.JPanel jPanelProcesses;
    private javax.swing.JScrollPane jScrollPaneMemory;
    private javax.swing.JScrollPane jScrollPaneProcesses;
    public ArrayList<JFormattedTextField> jTextMemory = new ArrayList<>();
    public int processes;
    public int processes_aborted;
    public int memory_size;
    public int previous_memory;
    public static int list_size;
    public String algorithm;
    FirstFit firstfit = new FirstFit();
    NextFit nextfit = new NextFit();
    BestFit bestfit = new BestFit();
    WorstFit worstfit = new WorstFit();
    QuickFit quickfit = new QuickFit();
    MergeFit mergefit = new MergeFit();
    public Timer timer;

    public GUI() {
        super("Gerenciador de Memória");
        initComponents();
        previous_memory = 0;
    }

    public void setup(int processes, int size) {
        Process p;
        int memsize;
        MemoryCell aux;
        switch (this.algorithm) {
            case "FirstFit":
                firstfit.setParameters(processes, size);

                for (int i = 0; i < processes; i++) {
                    p = firstfit.createProcess();
                    this.addAbleTextField(p.toString());
                }
                this.jPanelProcesses.getComponent(0).setBackground(Color.YELLOW);

                memsize = firstfit.getlistSize();
                aux = firstfit.trailer;
                for (int i = 1; i <= memsize; i++) {
                    if (aux.process("Get", null) == null) {
                        this.addMemoryTextField(aux.toString(), "Green");
                    } else {
                        this.addMemoryTextField(aux.toString(), "Red");
                    }

                    aux = aux.getNext();
                }
                new Thread(refresh).start();
                TimerRefreshProcesses();
                break;
            case "NextFit":
                nextfit.setParameters(processes, size);

                for (int i = 0; i < processes; i++) {
                    p = nextfit.createProcess();
                    this.addAbleTextField(p.toString());
                }

                memsize = nextfit.getlistSize();
                aux = nextfit.trailer;
                for (int i = 1; i <= memsize; i++) {
                    if (aux.process("Get", null) == null) {
                        this.addMemoryTextField(aux.toString(), "Green");
                    } else {
                        this.addMemoryTextField(aux.toString(), "Red");
                    }

                    aux = aux.getNext();
                }
                new Thread(refresh).start();
                TimerRefreshProcesses();
                break;

            case "BestFit":
                bestfit.setParameters(processes, size);

                for (int i = 0; i < processes; i++) {
                    p = bestfit.createProcess();
                    this.addAbleTextField(p.toString());
                }

                memsize = bestfit.getlistSize();
                aux = bestfit.trailer;
                for (int i = 1; i <= memsize; i++) {
                    if (aux.process("Get", null) == null) {
                        this.addMemoryTextField(aux.toString(), "Green");
                    } else {
                        this.addMemoryTextField(aux.toString(), "Red");
                    }

                    aux = aux.getNext();
                }
                new Thread(refresh).start();
                TimerRefreshProcesses();

                break;
            case "WorstFit":

                worstfit.setParameters(processes, size);

                for (int i = 0; i < processes; i++) {
                    p = worstfit.createProcess();
                    this.addAbleTextField(p.toString());
                }

                memsize = worstfit.getlistSize();
                aux = worstfit.trailer;
                for (int i = 1; i <= memsize; i++) {
                    if (aux.process("Get", null) == null) {
                        this.addMemoryTextField(aux.toString(), "Green");
                    } else {
                        this.addMemoryTextField(aux.toString(), "Red");
                    }

                    aux = aux.getNext();
                }
                new Thread(refresh).start();
                TimerRefreshProcesses();

                break;
            case "QuickFit":
                quickfit.setParameters(processes, size);

                for (int i = 0; i < processes; i++) {
                    p = quickfit.createProcess();
                    this.addAbleTextField(p.toString());
                }

                memsize = quickfit.getlistSize();
                aux = quickfit.trailer;
                for (int i = 1; i <= memsize; i++) {
                    if (aux.process("Get", null) == null) {
                        this.addMemoryTextField(aux.toString(), "Green");
                    } else {
                        this.addMemoryTextField(aux.toString(), "Red");
                    }

                    aux = aux.getNext();
                }
                new Thread(refresh).start();
                TimerRefreshProcesses();

                break;
            case "MergeFit":
                mergefit.setParameters(processes, size);

                for (int i = 0; i < processes; i++) {
                    p = mergefit.createProcess();
                    this.addAbleTextField(p.toString());
                }

                memsize = mergefit.getlistSize();
                aux = mergefit.trailer;
                for (int i = 1; i <= memsize; i++) {
                    if (aux.process("Get", null) == null) {
                        this.addMemoryTextField(aux.toString(), "Green");
                    } else {
                        this.addMemoryTextField(aux.toString(), "Red");
                    }

                    aux = aux.getNext();
                }
                new Thread(refresh).start();
                TimerRefreshProcesses();

                break;

        }

    }

    public void TimerRefreshProcesses() { // Periodic Timer, that executes every 3 seconds.
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                refreshProcesses();
            }
        };
        timer = new Timer(3000, actionListener);
        timer.start();

    }

    public void refreshProcesses() {
        jPanelProcesses.revalidate();
        if (jPanelProcesses.getComponentCount() > 0) {
            jPanelProcesses.remove(0);
            if (jPanelProcesses.getComponentCount() > 0) {
                jPanelProcesses.getComponent(0).setBackground(Color.YELLOW);
            }
        }

        jPanelProcesses.repaint();

    }

    public void refreshGui() {
        int memsize;
        String text;
        MemoryCell aux;
        switch (this.algorithm) {
            case "FirstFit":
                aux = firstfit.trailer;
                memsize = firstfit.getlistSize();
                if (memsize > previous_memory) {
                    for (int i = previous_memory + 1; i <= memsize; i++) {
                        if (aux.process("Get", null) == null) {
                            this.addMemoryTextField(aux.toString(), "Green");
                        } else {
                            this.addMemoryTextField(aux.toString(), "Red");
                        }
                        if (aux.getNext() != null) {
                            aux = aux.getNext();
                        }
                    }
                }
                aux = firstfit.trailer;
                for (int i = 1; i <= memsize; i++) {
                    if (aux != null) {
                        text = aux.toString();
                        this.jTextMemory.get(i - 1).setText(text);
                        if (aux.process("Get", null) != null) {
                            if (aux == firstfit.last && aux.process("Get", null) != null) {
                                this.jTextMemory.get(i - 1).setBackground(Color.yellow);
                            } else {
                                this.jTextMemory.get(i - 1).setBackground(new java.awt.Color(255, 0, 0));
                            }

                        } else {
                            this.jTextMemory.get(i - 1).setBackground(new java.awt.Color(102, 255, 102));

                        }
                        if (aux.getNext() != null) {
                            aux = aux.getNext();
                        }
                    }
                }
                this.previous_memory = memsize;
                this.jLabelMemoryOutPut.setText(Integer.toString(memory_size) + " Bytes");
                this.jLabelFreeOutput.setText(Integer.toString(firstfit.getfreeMemory()) + " Bytes");
                this.jLabelInUseOutPut.setText(Integer.toString(firstfit.getUsedMemory()) + " Bytes");
                this.jLabelTotalAbortedProcesses.setText("Número de processos abortados: " + Integer.toString(firstfit.abortedCount));
                this.jLabelBlocksOutput.setText(Integer.toString(firstfit.getlistSize()));
                this.jLabelAlgorithm.setText("Alocado: " + firstfit.memory_setted + "/" + firstfit.memory_size);
                break;
            case "NextFit":
                MemoryCell aux2;
                memsize = nextfit.blocks.size();
                if (memsize > previous_memory) {
                    for (int i = previous_memory; i < memsize; i++) {
                        aux2 = nextfit.blocks.get(i);
                        if (aux2.process("Get", null) == null) {
                            this.addMemoryTextField(aux2.toString(), "Green");
                        } else {
                            this.addMemoryTextField(aux2.toString(), "Red");
                        }
                    }
                }
                for (int i = 0; i < memsize; i++) {

                    aux2 = nextfit.blocks.get(i);
                    if (aux2 != null) {
                        text = aux2.toString();
                        this.jTextMemory.get(i).setText(text);
                        if (aux2.process("Get", null) != null) {
                            if (aux2 != nextfit.nextfit) {
                                this.jTextMemory.get(i).setBackground(new java.awt.Color(255, 0, 0));
                            }

                            if (aux2 == nextfit.nextfit) {
                                this.jTextMemory.get(i).setBackground(Color.BLUE);
                            }
                        } else {
                            if (aux2 != nextfit.nextfit) {
                                this.jTextMemory.get(i).setBackground(new java.awt.Color(102, 255, 102));
                            } else {
                                this.jTextMemory.get(i).setBackground(Color.YELLOW);
                            }
                        }
                    }
                }
                this.previous_memory = memsize;
                this.jLabelMemoryOutPut.setText(Integer.toString(memory_size) + " Bytes");
                this.jLabelFreeOutput.setText(Integer.toString(nextfit.getfreeMemory()) + " Bytes");
                this.jLabelInUseOutPut.setText(Integer.toString(nextfit.getUsedMemory()) + " Bytes");
                this.jLabelTotalAbortedProcesses.setText("Número de processos abortados: " + Integer.toString(nextfit.abortedCount));
                this.jLabelBlocksOutput.setText(Integer.toString(nextfit.getlistSize()));
                this.jLabelAlgorithm.setText("Alocado: " + nextfit.memory_setted + "/" + nextfit.memory_size);
                break;
            case "BestFit":
                aux = bestfit.trailer;
                memsize = bestfit.getlistSize();
                if (memsize > previous_memory) {
                    for (int i = previous_memory + 1; i <= memsize; i++) {
                        if (aux.process("Get", null) == null) {
                            this.addMemoryTextField(aux.toString(), "Green");
                        } else {
                            this.addMemoryTextField(aux.toString(), "Red");
                        }

                        if (aux.getNext() != null) {
                            aux = aux.getNext();
                        }
                    }
                }
                aux = bestfit.trailer;
                for (int i = 1; i <= memsize; i++) {
                    if (aux != null) {
                        text = aux.toString();
                        this.jTextMemory.get(i - 1).setText(text);
                        if (aux.process("Get", null) != null) {
                            if (aux == bestfit.last && aux.process("Get", null) != null) {
                                this.jTextMemory.get(i - 1).setBackground(Color.yellow);
                            } else {
                                this.jTextMemory.get(i - 1).setBackground(new java.awt.Color(255, 0, 0));
                            }

                        } else {
                            this.jTextMemory.get(i - 1).setBackground(new java.awt.Color(102, 255, 102));

                        }
                        if (aux.getNext() != null) {
                            aux = aux.getNext();
                        }
                    }
                }
                this.previous_memory = memsize;
                this.jLabelMemoryOutPut.setText(Integer.toString(memory_size) + " Bytes");
                this.jLabelFreeOutput.setText(Integer.toString(bestfit.getfreeMemory()) + " Bytes");
                this.jLabelInUseOutPut.setText(Integer.toString(bestfit.getUsedMemory()) + " Bytes");
                this.jLabelTotalAbortedProcesses.setText("Número de processos abortados: " + Integer.toString(bestfit.abortedCount));
                this.jLabelBlocksOutput.setText(Integer.toString(bestfit.getlistSize()));
                this.jLabelAlgorithm.setText("Alocado: " + bestfit.memory_setted + "/" + bestfit.memory_size);
                break;
            case "WorstFit":
                aux = worstfit.trailer;
                memsize = worstfit.getlistSize();
                if (memsize > previous_memory) {
                    for (int i = previous_memory + 1; i <= memsize; i++) {
                        if (aux.process("Get", null) == null) {
                            this.addMemoryTextField(aux.toString(), "Green");
                        } else {
                            this.addMemoryTextField(aux.toString(), "Red");
                        }

                        if (aux.getNext() != null) {
                            aux = aux.getNext();
                        }
                    }
                }
                aux = worstfit.trailer;
                for (int i = 1; i <= memsize; i++) {
                    if (aux != null) {
                        text = aux.toString();
                        this.jTextMemory.get(i - 1).setText(text);
                        if (aux.process("Get", null) != null) {
                            if (aux == worstfit.last && aux.process("Get", null) != null) {
                                this.jTextMemory.get(i - 1).setBackground(Color.yellow);
                            } else {
                                this.jTextMemory.get(i - 1).setBackground(new java.awt.Color(255, 0, 0));
                            }

                        } else {
                            this.jTextMemory.get(i - 1).setBackground(new java.awt.Color(102, 255, 102));

                        }
                        if (aux.getNext() != null) {
                            aux = aux.getNext();
                        }
                    }
                }
                this.previous_memory = memsize;
                this.jLabelMemoryOutPut.setText(Integer.toString(memory_size) + " Bytes");
                this.jLabelFreeOutput.setText(Integer.toString(worstfit.getfreeMemory()) + " Bytes");
                this.jLabelInUseOutPut.setText(Integer.toString(worstfit.getUsedMemory()) + " Bytes");
                this.jLabelTotalAbortedProcesses.setText("Número de processos abortados: " + Integer.toString(worstfit.abortedCount));
                this.jLabelBlocksOutput.setText(Integer.toString(worstfit.getlistSize()));
                this.jLabelAlgorithm.setText("Alocado: " + worstfit.memory_setted + "/" + worstfit.memory_size);
                break;
            case "QuickFit":
                aux = quickfit.trailer;
                memsize = quickfit.getlistSize();
                if (memsize > previous_memory) {
                    for (int i = previous_memory + 1; i <= memsize; i++) {
                        if (aux.process("Get", null) == null) {
                            this.addMemoryTextField(aux.toString(), "Green");
                        } else {
                            this.addMemoryTextField(aux.toString(), "Red");
                        }

                        if (aux.getNext() != null) {
                            aux = aux.getNext();
                        }
                    }
                }
                aux = quickfit.trailer;
                for (int i = 1; i <= memsize; i++) {
                    if (aux != null) {
                        text = aux.toString() + " @ QuickFit List: " + aux.getSize() + " B" + " , " + "Index:  " + "[" + aux.getIndex() + "]";
                        this.jTextMemory.get(i - 1).setText(text);
                        if (aux.process("Get", null) != null) {
                            if (aux == quickfit.last && aux.process("Get", null) != null) {
                                this.jTextMemory.get(i - 1).setBackground(Color.yellow);
                            } else {
                                this.jTextMemory.get(i - 1).setBackground(new java.awt.Color(255, 0, 0));
                            }

                        } else {
                            this.jTextMemory.get(i - 1).setBackground(new java.awt.Color(102, 255, 102));

                        }
                        if (aux.getNext() != null) {
                            aux = aux.getNext();
                        }
                    }
                }
                this.previous_memory = memsize;
                this.jLabelMemoryOutPut.setText(Integer.toString(memory_size) + " Bytes");
                this.jLabelFreeOutput.setText(Integer.toString(quickfit.getfreeMemory()) + " Bytes");
                this.jLabelInUseOutPut.setText(Integer.toString(quickfit.getUsedMemory()) + " Bytes");
                this.jLabelTotalAbortedProcesses.setText("Número de processos abortados: " + Integer.toString(quickfit.abortedCount));
                this.jLabelBlocksOutput.setText(Integer.toString(quickfit.getlistSize()));
                this.jLabelAlgorithm.setText("Alocado: " + quickfit.memory_setted + "/" + quickfit.memory_size);
                break;
            case "MergeFit":

                aux = mergefit.trailer;
                memsize = mergefit.getlistSize();
                if (memsize > previous_memory) {
                    for (int i = previous_memory + 1; i <= memsize; i++) {
                        if (aux.process("Get", null) == null) {
                            this.addMemoryTextField(aux.toString(), "Green");
                        } else {
                            this.addMemoryTextField(aux.toString(), "Red");
                        }
                        if (aux.getNext() != null) {
                            aux = aux.getNext();
                        }
                    }
                }
                aux = mergefit.trailer;
                for (int i = 1; i <= memsize; i++) {
                    if (aux != null) {
                        text = aux.toString();
                        this.jTextMemory.get(i - 1).setText(text);
                        if (aux.process("Get", null) != null) {
                            if (aux == mergefit.last && aux.process("Get", null) != null) {
                                this.jTextMemory.get(i - 1).setBackground(Color.yellow);
                            } else {
                                this.jTextMemory.get(i - 1).setBackground(new java.awt.Color(255, 0, 0));
                            }

                        } else {
                            this.jTextMemory.get(i - 1).setBackground(new java.awt.Color(102, 255, 102));

                        }
                        if (aux.getNext() != null) {
                            aux = aux.getNext();
                        }
                    }
                }
                break;
        }

    }
    public Runnable refresh = new Runnable() {
        @Override
        public void run() {
            while (true) {
                refreshGui();
            }
        }
    };

    public void createProcess() {
        Process p;

        switch (this.algorithm) {
            case "FirstFit":
                p = firstfit.createProcess();
                this.addAbleTextField(p.toString());
                break;
            case "NextFit":
                p = nextfit.createProcess();
                this.addAbleTextField(p.toString());

                break;
            case "BestFit":
                p = bestfit.createProcess();
                this.addAbleTextField(p.toString());
                break;
            case "WorstFit":
                p = worstfit.createProcess();
                this.addAbleTextField(p.toString());

                break;
            case "QuickFit":
                p = quickfit.createProcess();
                this.addAbleTextField(p.toString());
                break;
            case "MergeFit":
                p = mergefit.createProcess();
                this.addAbleTextField(p.toString());
                break;
        }
    }

    public void addMemoryTextField(String text, String color) {
        JFormattedTextField aux = new javax.swing.JFormattedTextField();
        if (color.compareTo("Green") == 0) {
            aux.setBackground(new java.awt.Color(102, 255, 102));
        }
        if (color.compareTo("Red") == 0) {
            aux.setBackground(new java.awt.Color(255, 0, 0));
        }
        aux.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        aux.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); 
        aux.setMaximumSize(new java.awt.Dimension(700, 30));
        aux.setMinimumSize(new java.awt.Dimension(700, 30));
        aux.setPreferredSize(new java.awt.Dimension(700, 30));
        this.jTextMemory.add(aux);
        jPanelMemory.add(aux);
        aux.setText(text);
        aux.setEditable(false);
        jPanelMemory.revalidate();

    }

    public void addAbleTextField(String text) {
        JFormattedTextField aux = new javax.swing.JFormattedTextField();
        aux.setBackground(new java.awt.Color(204, 204, 204));
        aux.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        aux.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 10)); 
        aux.setMaximumSize(new java.awt.Dimension(600, 30));
        aux.setMinimumSize(new java.awt.Dimension(600, 30));
        aux.setPreferredSize(new java.awt.Dimension(500, 20));
        jPanelProcesses.add(aux);
        aux.setText(text);
        jPanelProcesses.revalidate();
        this.processes_aborted++;

    }

   
    @SuppressWarnings("unchecked")
                            
    private void initComponents() {

        jLabelMemorySize = new javax.swing.JLabel();
        jFormattedTextFieldMemorySize = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jFormattedTextFieldProcesses = new javax.swing.JFormattedTextField();
        jLabelProcesses = new javax.swing.JLabel();
        jComboBox = new javax.swing.JComboBox();
        jButtonHelp = new javax.swing.JButton();
        jButtonStart = new javax.swing.JButton();
        jLabelMemoryBlocksJPanel = new javax.swing.JLabel();
        jScrollPaneMemory = new javax.swing.JScrollPane();
        jPanelMemory = new javax.swing.JPanel();
        jLabelAbortedProcessesJpanel = new javax.swing.JLabel();
        jButtonStop = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();
        jButtonNewProcess = new javax.swing.JButton();
        jScrollPaneProcesses = new javax.swing.JScrollPane();
        jPanelProcesses = new javax.swing.JPanel();
        jLabelTotalAbortedProcesses = new javax.swing.JLabel();
        jMemory = new javax.swing.JLabel();
        jLabelMemoryOutPut = new javax.swing.JLabel();
        jLabelInUse = new javax.swing.JLabel();
        jLabelInUseOutPut = new javax.swing.JLabel();
        jLabelFree = new javax.swing.JLabel();
        jLabelFreeOutput = new javax.swing.JLabel();
        jLabelBlocks = new javax.swing.JLabel();
        jLabelAlgorithm = new javax.swing.JLabel();
        jLabelBlocksOutput = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelMemorySize.setText("Tamanho da Memória:");

        jFormattedTextFieldMemorySize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldMemorySizeActionPerformed(evt);
            }
        });

        jLabel2.setText("Bytes");

        jFormattedTextFieldProcesses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldProcessesActionPerformed(evt);
            }
        });

        jLabelProcesses.setText("Número de Processos:");

        jComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"FirstFit", "QuickFit", "BestFit", "WorstFit", "NextFit"}));

        jButtonHelp.setText("?");
        jButtonHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHelpActionPerformed(evt);
            }
        });

        jButtonStart.setText("Iniciar Simulação");
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartActionPerformed(evt);
            }
        });

        jLabelMemoryBlocksJPanel.setText("Blocos de Memória:");

        jScrollPaneMemory.setBorder(null);
        jPanelMemory.setLayout(new javax.swing.BoxLayout(jPanelMemory, javax.swing.BoxLayout.Y_AXIS));
        jScrollPaneMemory.setViewportView(jPanelMemory);

        jLabelAbortedProcessesJpanel.setText("Processos:");
        jButtonStop.setVisible(false);
        jButtonStop.setText("Parar Simulação");
        jButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStopActionPerformed(evt);
            }
        });

        jButtonExit.setText("Sair ");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });
        jButtonNewProcess.setEnabled(false);
        jButtonNewProcess.setText("Novo Processo");
        jButtonNewProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewProcessActionPerformed(evt);
            }
        });

        jScrollPaneProcesses.setBorder(null);
        jScrollPaneProcesses.setMinimumSize(new java.awt.Dimension(0, 200));
        jScrollPaneProcesses.setPreferredSize(new java.awt.Dimension(0, 200));
        jPanelProcesses.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelProcesses.setMaximumSize(new java.awt.Dimension(0, 200));
        jPanelProcesses.setMinimumSize(new java.awt.Dimension(0, 200));
        jPanelProcesses.setLayout(new javax.swing.BoxLayout(jPanelProcesses, javax.swing.BoxLayout.Y_AXIS));
        jScrollPaneProcesses.setViewportView(jPanelProcesses);

        jLabelTotalAbortedProcesses.setText("Total de Processos Abortados: 0");

        jMemory.setText("Memória:");

        jLabelMemoryOutPut.setText("0 Bytes");

        jLabelInUse.setText("Em uso:");

        jLabelInUseOutPut.setForeground(new java.awt.Color(255, 0, 0));
        jLabelInUseOutPut.setText("0  Bytes");

        jLabelFree.setText("Disponível:");

        jLabelFreeOutput.setForeground(new java.awt.Color(0, 153, 0));
        jLabelFreeOutput.setText("0 Bytes");

        jLabelBlocks.setText("Blocos de Memória:");

        jLabelAlgorithm.setForeground(new java.awt.Color(0, 102, 204));
        jLabelAlgorithm.setText("Algoritmo:");

        jLabelBlocksOutput.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabelMemorySize)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jFormattedTextFieldMemorySize, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabelProcesses)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jFormattedTextFieldProcesses, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButtonHelp)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButtonStart))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPaneMemory, javax.swing.GroupLayout.PREFERRED_SIZE, 727, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabelMemoryBlocksJPanel))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addGap(0, 75, Short.MAX_VALUE)
                                                        .addComponent(jButtonStop)
                                                        .addGap(40, 40, 40)
                                                        .addComponent(jButtonNewProcess)
                                                        .addGap(40, 40, 40)
                                                        .addComponent(jButtonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(32, 32, 32))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabelFree)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabelFreeOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabelInUse)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabelInUseOutPut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jMemory)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabelMemoryOutPut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addComponent(jScrollPaneProcesses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabelTotalAbortedProcesses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabelAlgorithm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabelAbortedProcessesJpanel)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jLabelBlocks)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jLabelBlocksOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelMemorySize)
                                .addComponent(jFormattedTextFieldMemorySize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(jFormattedTextFieldProcesses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelProcesses)
                                .addComponent(jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonHelp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonStart))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelAbortedProcessesJpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelMemoryBlocksJPanel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPaneProcesses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabelTotalAbortedProcesses)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jMemory)
                                                .addComponent(jLabelMemoryOutPut))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabelInUse)
                                                .addComponent(jLabelInUseOutPut))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabelFree)
                                                .addComponent(jLabelFreeOutput))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabelBlocks)
                                                .addComponent(jLabelBlocksOutput))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelAlgorithm)
                                        .addGap(17, 17, 17)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jButtonStop)
                                                .addComponent(jButtonNewProcess)
                                                .addComponent(jButtonExit)))
                                .addComponent(jScrollPaneMemory, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }                       

    private void jFormattedTextFieldMemorySizeActionPerformed(java.awt.event.ActionEvent evt) {
        
    }

    private void jFormattedTextFieldProcessesActionPerformed(java.awt.event.ActionEvent evt) {
     
    }

    private void jButtonNewProcessActionPerformed(java.awt.event.ActionEvent evt) {
        this.createProcess();
    }

    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt) {
     
    }

    private void jButtonHelpActionPerformed(java.awt.event.ActionEvent evt) {
        JFrame frame = new JFrame();
        switch (this.jComboBox.getSelectedItem().toString()) {
            case "FirstFit":
                JOptionPane.showMessageDialog(frame, "O algoritmo FirstFit inicia a busca do início da lista de blocos \n "
                        + "e aloca o processo no primeiro bloco de tamanho igual ou superior  ao tamanho do processo.");
                break;
            case "NextFit":
                JOptionPane.showMessageDialog(frame, "O algoritmo NextFit é uma pequena variação do FirstFit. A busca por blocos de tamanho igual ou superior ao do processo \n"
                        + "inicia-se a partir do ponto da última alocação efetuada. Se a busca atingir o fim da lista, esta volta para o início, de forma circular.");
                break;
            case "BestFit":
                JOptionPane.showMessageDialog(frame, "O algoritmo BestFit busca um bloco de menor tamanho possível para o processo. Esse algoritmo diminui a \n"
                        + "fragmentação dos blocos.");
                break;
            case "WorstFit":
                JOptionPane.showMessageDialog(frame, "O algoritmo WorstFit é o oposto do NextFit, este busca um bloco de maior tamanho \n "
                        + "possível para o processo. Esse algoritmo aumenta a fragmentação dos blocos.");

                break;
            case "QuickFit":
                JOptionPane.showMessageDialog(frame, "O algoritmo QuickFit separa os blocos por listas de blocos de tamanho comum e faz a tentativa de \n"
                        + "alocação buscando nestas listas." + " Se um processo de 64 Bytes requisita alocação, o algorimto inicia a busca na lista dos \n"
                        + "blocos de 64 bytes e não havendo blocos disponíveis, inicia a busca nas listas de blocos de maior tamanho. Se a busca atingir "
                        + "o fim das listas de blocos e não houver alocação o processo é abortado.");
                break;
            case "MergeFit":

                break;
        }

    }

    private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {
        this.algorithm = this.jComboBox.getSelectedItem().toString();
        this.processes = Integer.parseInt(jFormattedTextFieldProcesses.getText());
        this.memory_size = Integer.parseInt(jFormattedTextFieldMemorySize.getText());
        this.setup(processes, memory_size);
        this.jButtonStart.setEnabled(false);
        this.jFormattedTextFieldMemorySize.setEnabled(false);
        this.jFormattedTextFieldProcesses.setEnabled(false);
        this.jComboBox.setEnabled(false);
        this.jButtonNewProcess.setEnabled(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

}
