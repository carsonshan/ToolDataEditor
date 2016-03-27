/**
 * modified by Herbert Roider <herbert@roider.at>
 */

package draganbjedov.netbeans.tooldata.view;

//import draganbjedov.netbeans.tooldata.Bundle;
import draganbjedov.netbeans.tooldata.dataobject.TOOLDATADataObject;
import draganbjedov.netbeans.tooldata.view.ccp.TableRowTransferable;
import draganbjedov.netbeans.tooldata.view.ccp.TableTransferHandler;
import draganbjedov.netbeans.tooldata.view.ccp.TransferActionListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;
import static org.jdesktop.swingx.JXTable.USE_DTCR_COLORMEMORY_HACK;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.awt.ToolbarWithOverflow;
import org.openide.awt.UndoRedo;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;
//import org.oxbow.swingbits.table.filter.TableRowFilterSupport;

@MultiViewElement.Registration(
        displayName = "#LBL_TOOLDATA_VISUAL",
        iconBase = "draganbjedov/netbeans/tooldata/icons/tooldata.png",
        mimeType = "text/x-tooldata",
        persistenceType = TopComponent.PERSISTENCE_NEVER,
        preferredID = "TooldataVisual",
        position = 1000)
@Messages("LBL_TOOLDATA_VISUAL=Table")
public final class TOOLDATAVisualElement extends JPanel implements MultiViewElement {

    private static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();

    private final TOOLDATADataObject obj;
    private final JToolBar toolbar = new ToolbarWithOverflow();

    private transient MultiViewElementCallback callback;

    private final transient TOOLDATATableModel tableModel;
    //private TableRowFilterSupport tableRowFilterSupport;

    private boolean activated;

    private AbstractAction addRowAction;
    private AbstractAction deleteRowAction;
//	private AbstractAction addColumnAction;
//	private AbstractAction deleteColumnAction;
//	private AbstractAction renameColumnAction;

    private AbstractAction moveTopAction;
    private AbstractAction moveUpAction;
    private AbstractAction moveDownAction;
    private AbstractAction moveBottomAction;

//	private AbstractAction moveHomeAction;
//	private AbstractAction moveLeftAction;
//	private AbstractAction moveRightAction;
//	private AbstractAction moveEndAction;
//
//	private AbstractAction separatorChangedAction;
    private AbstractAction cutAction;
    private AbstractAction copyAction;
    private AbstractAction pasteAction;

    private final Lookup lookup;
    private final InstanceContent instanceContent;

    @SuppressWarnings("LeakingThisInConstructor")
    public TOOLDATAVisualElement(Lookup objLookup) {
        obj = objLookup.lookup(TOOLDATADataObject.class);
        assert obj != null;
        instanceContent = new InstanceContent();
        lookup = new ProxyLookup(objLookup, new AbstractLookup(instanceContent));

        tableModel = new TOOLDATATableModel();

        initActions();
        initComponents();
        init();
        createToolBar();

        obj.setVisualEditor(this);

        updateTable();
    }

    @Override
    public String getName() {
        return "TOOLDATAVisualElement";
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tablePopUpMenu = new javax.swing.JPopupMenu();
        copyPopUp = new javax.swing.JMenuItem();
        cutPopUp = new javax.swing.JMenuItem();
        pastePopUp = new javax.swing.JMenuItem();
        separator1 = new javax.swing.JPopupMenu.Separator();
        addRowPopUp = new javax.swing.JMenuItem();
        deleteRowPopUp = new javax.swing.JMenuItem();
        separator3 = new javax.swing.JPopupMenu.Separator();
        moveTopPopUp = new javax.swing.JMenuItem();
        moveUpPopUp = new javax.swing.JMenuItem();
        moveDownPopUp = new javax.swing.JMenuItem();
        moveBottomPopUp = new javax.swing.JMenuItem();
        tableScrollPane = new javax.swing.JScrollPane();
        table = new org.jdesktop.swingx.JXTable(){

            @Override
            public Component prepareEditor(TableCellEditor editor, int row, int column) {
                final Component c = super.prepareEditor(editor, row, column);

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run(){
                        if (c instanceof JTextComponent) {
                            ((JTextComponent) c).selectAll();
                        }
                    }
                });

                addRowAction.setEnabled(false);
                deleteRowAction.setEnabled(false);

                moveTopAction.setEnabled(false);
                moveUpAction.setEnabled(false);
                moveDownAction.setEnabled(false);
                moveBottomAction.setEnabled(false);

                cutAction.setEnabled(false);
                copyAction.setEnabled(false);
                pasteAction.setEnabled(false);

                //separators.setEnabled(false);
                return c;
            }

            public boolean editCellAt(int row, int column, EventObject e){
                if(e instanceof KeyEvent){
                    int i = ((KeyEvent) e).getModifiers();
                    String s = KeyEvent.getModifiersExText(((KeyEvent) e).getModifiers());
                    //any time Control is used, disable cell editing
                    if(i == InputEvent.CTRL_MASK){
                        return false;
                    }
                }
                return super.editCellAt(row, column, e);
            }
        };

        tablePopUpMenu.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                tablePopUpMenuPopupMenuWillBecomeVisible(evt);
            }
        });

        copyPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyPopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/copy.gif"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(copyPopUp, org.openide.util.NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.copyPopUp.text")); // NOI18N
        tablePopUpMenu.add(copyPopUp);

        cutPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cutPopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/cut.gif"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(cutPopUp, org.openide.util.NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.cutPopUp.text")); // NOI18N
        tablePopUpMenu.add(cutPopUp);

        pastePopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        pastePopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/paste.gif"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(pastePopUp, org.openide.util.NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.pastePopUp.text")); // NOI18N
        tablePopUpMenu.add(pastePopUp);
        tablePopUpMenu.add(separator1);

        addRowPopUp.setAction(addRowAction);
        addRowPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_INSERT, 0));
        addRowPopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/add-row.gif"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(addRowPopUp, org.openide.util.NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.addRowButton.text")); // NOI18N
        addRowPopUp.setActionCommand(org.openide.util.NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.addRowPopUp.actionCommand")); // NOI18N
        tablePopUpMenu.add(addRowPopUp);
        addRowPopUp.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.addRowPopUp.AccessibleContext.accessibleDescription")); // NOI18N

        deleteRowPopUp.setAction(deleteRowAction);
        deleteRowPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        deleteRowPopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/remove-row.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(deleteRowPopUp, org.openide.util.NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.deleteRowButton.text")); // NOI18N
        tablePopUpMenu.add(deleteRowPopUp);
        tablePopUpMenu.add(separator3);

        moveTopPopUp.setAction(moveTopAction);
        moveTopPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_UP, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        moveTopPopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/go-top.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(moveTopPopUp, org.openide.util.NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.moveTopPopUp.text")); // NOI18N
        tablePopUpMenu.add(moveTopPopUp);

        moveUpPopUp.setAction(moveUpAction);
        moveUpPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_UP, java.awt.event.InputEvent.CTRL_MASK));
        moveUpPopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/go-up.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(moveUpPopUp, org.openide.util.NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.moveUpPopUp.text")); // NOI18N
        tablePopUpMenu.add(moveUpPopUp);

        moveDownPopUp.setAction(moveDownAction);
        moveDownPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DOWN, java.awt.event.InputEvent.CTRL_MASK));
        moveDownPopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/go-down.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(moveDownPopUp, org.openide.util.NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.moveDownPopUp.text")); // NOI18N
        tablePopUpMenu.add(moveDownPopUp);

        moveBottomPopUp.setAction(moveBottomAction);
        moveBottomPopUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DOWN, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        moveBottomPopUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/go-bottom.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(moveBottomPopUp, org.openide.util.NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.moveBottomPopUp.text")); // NOI18N
        tablePopUpMenu.add(moveBottomPopUp);

        setLayout(new java.awt.BorderLayout());

        table.setAutoCreateRowSorter(false);
        table.setModel(tableModel);
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table.setDragEnabled(true);
        table.setDropMode(javax.swing.DropMode.ON_OR_INSERT_ROWS);
        table.setPreferredScrollableViewportSize(new java.awt.Dimension(0, 800));
        table.setRowHeight(25);
        table.setRowSorter(null);
        table.setSearchable(null);
        table.setSortable(false);
        table.setSortsOnUpdates(false);
        tableScrollPane.setViewportView(table);

        add(tableScrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void tablePopUpMenuPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_tablePopUpMenuPopupMenuWillBecomeVisible
        boolean enabled = table.getSelectedRowCount() > 0;
        copyAction.setEnabled(enabled);
        cutAction.setEnabled(enabled);

        pasteAction.setEnabled(CLIPBOARD.isDataFlavorAvailable(TableRowTransferable.TOOLDATA_ROWS_DATA_FLAVOR));
    }//GEN-LAST:event_tablePopUpMenuPopupMenuWillBecomeVisible

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addRowPopUp;
    private javax.swing.JMenuItem copyPopUp;
    private javax.swing.JMenuItem cutPopUp;
    private javax.swing.JMenuItem deleteRowPopUp;
    private javax.swing.JMenuItem moveBottomPopUp;
    private javax.swing.JMenuItem moveDownPopUp;
    private javax.swing.JMenuItem moveTopPopUp;
    private javax.swing.JMenuItem moveUpPopUp;
    private javax.swing.JMenuItem pastePopUp;
    private javax.swing.JPopupMenu.Separator separator1;
    private javax.swing.JPopupMenu.Separator separator3;
    private org.jdesktop.swingx.JXTable table;
    private javax.swing.JPopupMenu tablePopUpMenu;
    private javax.swing.JScrollPane tableScrollPane;
    // End of variables declaration//GEN-END:variables
	private JButton addRowButton;
    private JButton deleteRowButton;
//	private JButton addColumnButton;
//	private JButton deleteColumnButton;
//	private JButton renameColumnButton;
    private JButton moveTop;
    private JButton moveUp;
    private JButton moveDown;
    private JButton moveBottom;
//	private JButton moveHome;
//	private JButton moveLeft;
//	private JButton moveRight;
//	private JButton moveEnd;
//    private JButton clearFilters;
//    private JButton setColumnsWidth;
//    private JComboBox separators;
    //JLabel picture;

//    class ComboItem {
//
//        private int key;
//        private String caption;
//
//        public ComboItem(int key, String caption) {
//            this.key = key;
//            this.caption = caption;
//        }
//
//        @Override
//        public String toString() {
//            return String.valueOf(key);
//        }
//
//        public int getKey() {
//            return key;
//        }
//
//        public String getCaption() {
//            return caption;
//        }
//    }

    @Override
    public JComponent getVisualRepresentation() {
        return this;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        return toolbar;
    }

    @Override
    public Action[] getActions() {
        if (callback != null) {
            return callback.createDefaultActions();
        }
        return new Action[0];
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    @Override
    public void componentOpened() {
        if (callback != null) {
            callback.updateTitle(obj.getPrimaryFile().getNameExt());
        }
    }

    @Override
    public void componentClosed() {
    }

    @Override
    public void componentShowing() {
        updateTable();
    }

    @Override
    public void componentHidden() {
    }

    @Override
    public void componentActivated() {
        activated = true;
        if (callback != null) {
            callback.updateTitle(obj.getPrimaryFile().getNameExt());
        }
        pasteAction.setEnabled(CLIPBOARD.isDataFlavorAvailable(TableRowTransferable.TOOLDATA_ROWS_DATA_FLAVOR));

        tableModel.fireTableDataChanged();
    }

    @Override
    public void componentDeactivated() {
        activated = false;
    }

    public boolean isActivated() {
        return activated;
    }

    @Override
    public UndoRedo getUndoRedo() {
        return obj.getUndoRedoManager();
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
        this.callback = callback;
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }

    @Messages({
        "BUTTON_CLEAR_FILTERS=Clear all filters",
        "BUTTON_COL_WIDTH=Set column widths"
    })
    private void createToolBar() {
        toolbar.setFloatable(false);

        addRowButton = new JButton(addRowAction);
        addRowButton.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.addRowButton.text") + " (Insert)");
        toolbar.add(addRowButton);

        deleteRowButton = new JButton(deleteRowAction);
        deleteRowButton.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.deleteRowButton.text") + " (Delete)");
        toolbar.add(deleteRowButton);

//		addColumnButton = new JButton(addColumnAction);
//		addColumnButton.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.addColumnButton.text") + " (Ctrl+Insert)");
//		toolbar.add(addColumnButton);
//
//		deleteColumnButton = new JButton(deleteColumnAction);
//		deleteColumnButton.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.deleteColumnButton.text") + " (Crtl+Delete)");
//		toolbar.add(deleteColumnButton);
//
//		renameColumnButton = new JButton(renameColumnAction);
//		renameColumnButton.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.renameColumnButton.text") + " (Crtl+R)");
//		toolbar.add(renameColumnButton);
        toolbar.addSeparator();

        //Move row actions
        moveTop = new JButton(moveTopAction);
        moveTop.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.moveTopPopUp.text") + " (Ctrl+Shift+Up)");
        toolbar.add(moveTop);

        moveUp = new JButton(moveUpAction);
        moveUp.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.moveUpPopUp.text") + " (Ctrl+Up)");
        toolbar.add(moveUp);

        moveDown = new JButton(moveDownAction);
        moveDown.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.moveDownPopUp.text") + " (Ctrl+Down)");
        toolbar.add(moveDown);

        moveBottom = new JButton(moveBottomAction);
        moveBottom.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.moveBottomPopUp.text") + " (Ctrl+Shift+Down)");
        toolbar.add(moveBottom);

        //toolbar.addSeparator();
        //Move column actions
//		moveHome = new JButton(moveHomeAction);
//		moveHome.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.moveHomePopUp.text") + " (Ctrl+Shift+Left)");
//		toolbar.add(moveHome);
//
//		moveLeft = new JButton(moveLeftAction);
//		moveLeft.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.moveLeftPopUp.text") + " (Ctrl+Left)");
//		toolbar.add(moveLeft);
//
//		moveRight = new JButton(moveRightAction);
//		moveRight.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.moveRightPopUp.text") + " (Ctrl+Right)");
//		toolbar.add(moveRight);
//
//		moveEnd = new JButton(moveEndAction);
//		moveEnd.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.moveEndPopUp.text") + " (Ctrl+Shift+Left)");
//		toolbar.add(moveEnd);
//
//		toolbar.addSeparator();
//		clearFilters = new JButton(ImageUtilities.loadImageIcon("draganbjedov/netbeans/csv/icons/filter-clear.png", false));
//		clearFilters.setToolTipText(Bundle.BUTTON_CLEAR_FILTERS());
//		//clearFilters.addActionListener((e) -> tableRowFilterSupport.clearAllFilters());
//		toolbar.add(clearFilters);
//
//		setColumnsWidth = new JButton(ImageUtilities.loadImageIcon("draganbjedov/netbeans/csv/icons/resize.png", false));
//		setColumnsWidth.setToolTipText(Bundle.BUTTON_COL_WIDTH());
//		setColumnsWidth.addActionListener((e) -> table.packAll());
//		toolbar.add(setColumnsWidth);
        //toolbar.addSeparator();
        //Separator
//		toolbar.add(new JLabel(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.separatorLabel.text")));
//
//		int customSeparatorCount = OptionsUtils.readCustomSeparatorCount();
//		if (customSeparatorCount > 0) {
//			List<Character> chars = OptionsUtils.readCustomSeparators(customSeparatorCount);
//			List<String> s = new ArrayList<>();
//			s.add(0, ",");
//			s.add(1, ";");
//			s.add(2, "Tab");
//			chars.stream().forEach(c -> s.add(c.toString()));
//			separators = new JComboBox(new DefaultComboBoxModel(s.toArray()));
//		} else
//			separators = new JComboBox(new String[]{",", ";", "Tab"});
//		toolbar.add(separators);
//		separators.setPreferredSize(new Dimension(100, separators.getPreferredSize().height));
//		separators.setMaximumSize(new Dimension(100, separators.getPreferredSize().height));
//		separators.setMinimumSize(new Dimension(100, separators.getPreferredSize().height));
//		separators.setToolTipText(NbBundle.getMessage(TOOLDATAVisualElement.class, "TOOLDATAVisualElement.separators.tooltip"));
//		separators.addActionListener(separatorChangedAction);
//		separators.setSelectedItem(OptionsUtils.readDefaultSeparator());
    }

    public void updateTable() {
        obj.readFile(tableModel);

//		updateColumnsWidths();
        table.packAll();
        setActiveActions();

//        TableColumn edgeposition = table.getColumnModel().getColumn(4);
//        String[] edgeStrings = {"", "innen raus", "innen rein", "aussen rein", "aussen raus", "plan hinten", "innen", "plan vorne", "aussen", "center"};
//        JComboBox cmbEdgepos = new JComboBox(edgeStrings);
//
////        JLabel picture = new JLabel();
////        picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
////        picture.setHorizontalAlignment(JLabel.CENTER);
////        updateLabel(edgeStrings[cmbEdgepos.getSelectedIndex()]);
////        picture.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
//
//        edgeposition.setCellEditor(new DefaultCellEditor(cmbEdgepos));
//
//        TableColumn tooltype = table.getColumnModel().getColumn(3);
//        JComboBox cmbTooltype = new JComboBox();
//        cmbTooltype.addItem(new ComboItem(100, "Schaftfräser"));
//        cmbTooltype.addItem(new ComboItem(200, "Bohrer"));
//        cmbTooltype.addItem(new ComboItem(500, "Drehwerkzeug"));
//
//        
//        tooltype.setCellEditor(new DefaultCellEditor(cmbTooltype));
    }

//    protected void updateLabel(String name) {
//        ImageIcon icon = createImageIcon("images/" + name + ".gif");
//        picture.setIcon(icon);
//        picture.setToolTipText("A drawing of a " + name.toLowerCase());
//        if (icon != null) {
//            picture.setText(null);
//        } else {
//            picture.setText("Image not found");
//        }
//    }
//
//    /**
//     * Returns an ImageIcon, or null if the path was invalid.
//     */
//    protected static ImageIcon createImageIcon(String path) {
//        java.net.URL imgURL = TOOLDATAVisualElement.class.getResource(path);
//        if (imgURL != null) {
//            return new ImageIcon(imgURL);
//        } else {
//            System.err.println("Couldn't find file: " + path);
//            return null;
//        }
//    }

    private void setActiveActions() {
        addRowAction.setEnabled(true);
        //addColumnAction.setEnabled(true);
        //deleteColumnAction.setEnabled(true);
        //renameColumnAction.setEnabled(true);
        //separators.setEnabled(true);
        final boolean hasSelectedRow = table.getSelectedRowCount() >= 1;
        deleteRowAction.setEnabled(hasSelectedRow);
        cutAction.setEnabled(hasSelectedRow);
        copyAction.setEnabled(hasSelectedRow);
        pasteAction.setEnabled(CLIPBOARD.isDataFlavorAvailable(TableRowTransferable.TOOLDATA_ROWS_DATA_FLAVOR));

        int[] rows = table.getSelectedRows();
        if (moveTop != null && moveUp != null && moveDown != null && moveBottom != null) {
            switch (rows.length) {
                case 0:
                    moveTopAction.setEnabled(false);
                    moveUpAction.setEnabled(false);
                    moveDownAction.setEnabled(false);
                    moveBottomAction.setEnabled(false);
                    break;
                case 1:
                    moveTopAction.setEnabled(rows[0] != 0);
                    moveUpAction.setEnabled(rows[0] != 0);
                    moveDownAction.setEnabled(rows[0] != table.getRowCount() - 1);
                    moveBottomAction.setEnabled(rows[0] != table.getRowCount() - 1);
                    break;
                default:
                    moveTopAction.setEnabled(true);
                    moveBottomAction.setEnabled(true);
                    int prev = rows[0];
                    for (int i = 1; i < rows.length; i++) {
                        if (prev != rows[i] - 1) {
                            moveUpAction.setEnabled(false);
                            moveDownAction.setEnabled(false);
                            return;
                        } else {
                            prev = rows[i];
                        }
                    }	//Continious top rows
                    final boolean topRows = rows[0] != 0;
                    moveTopAction.setEnabled(topRows);
                    moveUpAction.setEnabled(topRows);
                    //Continious rows at bottom
                    final boolean bottomRows = rows[rows.length - 1] != table.getRowCount() - 1;
                    moveDownAction.setEnabled(bottomRows);
                    moveBottomAction.setEnabled(bottomRows);
                    break;
            }
        }

//		int[] columns = table.getSelectedColumns();
//		if (moveHome != null && moveLeft != null && moveRight != null && moveEnd != null) {
//			switch (columns.length) {
//				case 0:
//					moveHomeAction.setEnabled(false);
//					moveLeftAction.setEnabled(false);
//					moveRightAction.setEnabled(false);
//					moveEndAction.setEnabled(false);
//					break;
//				case 1:
//					moveHomeAction.setEnabled(columns[0] != 0);
//					moveLeftAction.setEnabled(columns[0] != 0);
//					moveRightAction.setEnabled(columns[0] != table.getColumnCount() - 1);
//					moveEndAction.setEnabled(columns[0] != table.getColumnCount() - 1);
//					break;
//				default:
//					moveHomeAction.setEnabled(true);
//					moveEndAction.setEnabled(true);
//					int prev = columns[0];
//					for (int i = 1; i < columns.length; i++) {
//						if (prev != columns[i] - 1) {
//							moveLeftAction.setEnabled(false);
//							moveRightAction.setEnabled(false);
//							return;
//						} else {
//							prev = columns[i];
//						}
//					}
//					//Continious home columns
//					final boolean homeColumns = columns[0] != 0;
//					moveHomeAction.setEnabled(homeColumns);
//					moveLeftAction.setEnabled(homeColumns);
//					//Continious colums at the end
//					final boolean endColumns = columns[columns.length - 1] != table.getColumnCount() - 1;
//					moveRightAction.setEnabled(endColumns);
//					moveEndAction.setEnabled(endColumns);
//					break;
//			}
//		}
    }

    private void initActions() {
        addRowAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/add-row.gif"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    tableModel.addRow();
                    selectRow(table.getRowCount() - 1);
                } else {
                    tableModel.insertRow(selectedRow + 1);
                    selectRow(selectedRow + 1);
                }
            }
        };
        deleteRowAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/remove-row.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] rows = table.getSelectedRows();
                if (rows.length > 0) {
                    tableModel.removeRows(rows);
                    int row = rows[0] - 1;
                    if (row < 0) {
                        if (table.getRowCount() > 0) {
                            selectRow(0);
                        }
                    } else {
                        selectRow(row);
                    }
                }
            }
        };
//		addColumnAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/add-column.png"))) {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Pair<Integer, String> indexNewName = AddColumnDialog.show(tableModel.getHeaders());
//				if (indexNewName != null) {
//					int index = indexNewName.first();
//					String columnName = indexNewName.second();
//					if (index > tableModel.getColumnCount()) {
//						tableModel.addColumn(columnName);
//						selectColumn(table.getColumnCount() - 1);
//					} else {
//						tableModel.addColumn(columnName, index);
//						selectColumn(index);
//					}
////					updateColumnsWidths();
//					table.packAll();
//				}
//			}
//		};
//		deleteColumnAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/remove-column.png"))) {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Integer columnIndex = RemoveColumnDialog.show(tableModel.getHeaders());
//				if (columnIndex != null) {
//					tableModel.removeColumn(columnIndex);
////					updateColumnsWidths();
//					table.packAll();
//				}
//			}
//		};
//		renameColumnAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/rename-column.png"))) {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Pair<Integer, String> indexNewName = RenameColumnDialog.show(tableModel.getHeaders());
//				if (indexNewName != null) {
//					final Integer index = indexNewName.first();
//					final String newName = indexNewName.second();
//					final int viewIndex = table.convertColumnIndexToView(index);
//
//					tableModel.renameColumn(index, newName);
//
//					TableColumn column = table.getColumn(viewIndex);
//					column.setHeaderValue(newName);
//					updateColumnWidth(viewIndex);
//
//					tableModel.fireTableDataChanged();
//					selectColumn(viewIndex);
//					table.requestFocusInWindow();
//					table.packAll();
//				}
//			}
//		};

        moveTopAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/go-top.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rows[] = table.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    int row = rows[i];
                    int to = i;
                    tableModel.moveRow(row, to);
                }

                tableModel.fireTableRowsUpdated(0, table.getRowCount() - 1);
                selectRowInterval(0, rows.length - 1);
            }
        };
        moveUpAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/go-up.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rows[] = table.getSelectedRows();
                if (rows.length > 0) {
                    int row = rows[0] - 1;
                    int to = rows[rows.length - 1] + 1;
                    if (row >= 0) {
                        tableModel.moveRow(row, to);
                        tableModel.fireTableRowsUpdated(rows[0] - 1, rows[0] + rows.length - 1);
                        selectRowInterval(rows[0] - 1, rows[0] - 1 + rows.length - 1);
                    }
                }
            }
        };
        moveDownAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/go-down.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rows[] = table.getSelectedRows();
                if (rows.length > 0) {
                    int row = rows[rows.length - 1] + 1;
                    int to = table.getSelectedRow();
                    if (row <= table.getRowCount() - 1) {
                        tableModel.moveRow(row, to);
                        tableModel.fireTableRowsUpdated(rows[0], rows[0] + 1 + rows.length - 1);
                        selectRowInterval(rows[0] + 1, rows[0] + 1 + rows.length - 1);
                    }
                }
            }
        };
        moveBottomAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/tooldata/icons/go-bottom.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rows[] = table.getSelectedRows();
                for (int i = 0; i < rows.length; i++) {
                    int row = rows[i] - i;
                    tableModel.moveRow(row, table.getRowCount());
                }

                tableModel.fireTableRowsUpdated(0, table.getRowCount() - 1);
                selectRowInterval(table.getRowCount() - rows.length, table.getRowCount() - 1);
            }
        };

//		moveHomeAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-home.png"))) {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int[] columns = table.getSelectedColumns();
//				for (int i = 0; i < columns.length; i++) {
//					int column = columns[i];
//					int to = i;
//
//					TableColumnModel columnModel = table.getColumnModel();
//
//					HashMap<String, Integer> columnWidthHashMap = getColumnsWidths(columnModel);
//					tableModel.moveColumn(column, to);
//					setColumnsWidths(columnModel, columnWidthHashMap);
//				}
//				tableModel.fireTableDataChanged();
//				selectColumnInterval(0, columns.length - 1);
//			}
//		};
//		moveLeftAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-left.png"))) {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int[] columns = table.getSelectedColumns();
//				if (table.getSelectedColumn() != 0 && table.getSelectedColumn() != -1) {
//					int column = table.getSelectedColumn() - 1;
//					int to = columns[columns.length - 1];
//					if (column >= 0) {
//						TableColumnModel columnModel = table.getColumnModel();
//
//						HashMap<String, Integer> columnWidthHashMap = getColumnsWidths(columnModel);
//						tableModel.moveColumn(column, to);
//						setColumnsWidths(columnModel, columnWidthHashMap);
//
//						tableModel.fireTableDataChanged();
//						selectColumnInterval(columns[0] - 1, columns[columns.length - 1] - 1);
//					}
//				}
//			}
//		};
//		moveRightAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-right.png"))) {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int[] columns = table.getSelectedColumns();
//				if (table.getSelectedColumn() != -1) {
//					int column = columns[columns.length - 1] + 1;
//					int to = table.getSelectedColumn();
//					if (column <= table.getColumnCount() - 1) {
//						TableColumnModel columnModel = table.getColumnModel();
//
//						HashMap<String, Integer> columnWidthHashMap = getColumnsWidths(columnModel);
//						tableModel.moveColumn(column, to);
//						setColumnsWidths(columnModel, columnWidthHashMap);
//
//						tableModel.fireTableDataChanged();
//						selectColumnInterval(columns[0] + 1, columns[columns.length - 1] + 1);
//					}
//				}
//			}
//		};
//		moveEndAction = new AbstractAction("", new ImageIcon(getClass().getResource("/draganbjedov/netbeans/csv/icons/go-end.png"))) {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int[] columns = table.getSelectedColumns();
//				for (int i = 0; i < columns.length; i++) {
//					int column = columns[i] - i;
//
//					TableColumnModel columnModel = table.getColumnModel();
//
//					HashMap<String, Integer> columnWidthHashMap = getColumnsWidths(columnModel);
//					tableModel.moveColumn(column, table.getColumnCount() - 1);
//					setColumnsWidths(columnModel, columnWidthHashMap);
//				}
//				tableModel.fireTableDataChanged();
//				selectColumnInterval(table.getColumnCount() - columns.length, table.getColumnCount() - 1);
//			}
//		};
//		separatorChangedAction = new AbstractAction() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				updateTable();
//			}
//		};
    }

    @NbBundle.Messages({
        "ACTION_CUT=Cut",
        "ACTION_COPY=Copy",
        "ACTION_PASTE=Paste"
    })
    private void init() {
        //Table
        final RowNumberTable rowNumberTable = new RowNumberTable(table, false, "#");
        tableScrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowNumberTable.getTableHeader());
        tableScrollPane.setRowHeaderView(rowNumberTable);

        final ListSelectionListener listSelectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setActiveActions();
            }
        };
        table.getSelectionModel().addListSelectionListener(listSelectionListener);
        //table.getColumnModel().getSelectionModel().addListSelectionListener(listSelectionListener);

        //table.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        //table.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setRowSelectionAllowed(true);
        //table.setColumnSelectionAllowed(true);
        //table.setCellSelectionEnabled(true);

        /* Popravljalje visine redova zbog editovanja. Windows i Metal LAF nemaju margine u tekst poljima, a ostali imaju */
        LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
        boolean lafNotExpand = lookAndFeel.getID().toLowerCase().contains("windows") || lookAndFeel.getID().toLowerCase().contains("metal");

        /* 
		 * Da se ne bi ponistavala boja koju postavi renderer posto ne koristimo Highlighter iz SwingX-a
		 * Videti JXTable.prepareRenderer() i 
		 * JXTable.resetDefaultTableCellRendererColors()
         */
        table.putClientProperty(USE_DTCR_COLORMEMORY_HACK, false);

        table.setRowHeight(lafNotExpand ? 25 : 27);

        table.setShowGrid(true);
        table.setGridColor(new Color(128, 128, 128, 85));

        table.getDefaultEditor(String.class).addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                setActiveActions();
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
                setActiveActions();
            }
        });
        table.setDefaultRenderer(String.class, new OddEvenCellRenderer());

        final String lafName = UIManager.getLookAndFeel().getName();
        boolean setBackground = lafName.equals("Nimbus");

        if (setBackground) {
            table.getTableHeader().setBackground(Color.WHITE);
        }

        if (!lafName.startsWith("GTK")) {
            tableScrollPane.getRowHeader().setBackground(Color.WHITE);
            tableScrollPane.getViewport().setBackground(Color.WHITE);
            tableScrollPane.setBackground(Color.WHITE);
        } else {
            rowNumberTable.setBackground(table.getTableHeader().getBackground());
        }

        final JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setReorderingAllowed(false);

        tableModel.addTableModelListener((TableModelEvent e) -> obj.updateFile(tableModel));
        table.setModel(tableModel);
        tableScrollPane.setViewportView(table);

//		tableRowFilterSupport = TableRowFilterSupport.forTable(table);
//		tableRowFilterSupport.searchable(true);
//		tableRowFilterSupport.actions(true);
//		tableRowFilterSupport.useTableRenderers(true);
//		tableRowFilterSupport.apply();
////		tableRowFilterSupport.addChangeListener(filter -> {
//			setActiveActions();
//			tableModel.fireTableDataChanged();
//		});
        //Toolbar buttons
        KeyStroke strokeAddRow = KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0);
        KeyStroke strokeRemoveRow = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
//		KeyStroke strokeAddColumn = KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, InputEvent.CTRL_DOWN_MASK);
//		KeyStroke strokeRemoveColumn = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_DOWN_MASK);
//		KeyStroke strokeRenameColumn = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK);
        KeyStroke strokeEscape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

        this.getInputMap().put(strokeAddRow, "INSERT_ROW_COMMAND");
        this.getActionMap().put("INSERT_ROW_COMMAND", addRowAction);
        this.getInputMap().put(strokeRemoveRow, "DELETE_ROW_COMMAND");
        this.getActionMap().put("DELETE_ROW_COMMAND", deleteRowAction);

        table.getInputMap().put(strokeAddRow, "INSERT_ROW_COMMAND");
        table.getActionMap().put("INSERT_ROW_COMMAND", addRowAction);
        table.getInputMap().put(strokeRemoveRow, "DELETE_ROW_COMMAND");
        table.getActionMap().put("DELETE_ROW_COMMAND", deleteRowAction);

//		this.getInputMap().put(strokeAddColumn, "INSERT_COLUMN_COMMAND");
//		this.getActionMap().put("INSERT_COLUMN_COMMAND", addColumnAction);
//		this.getInputMap().put(strokeRemoveColumn, "DELETE_COLUMN_COMMAND");
//		this.getActionMap().put("DELETE_COLUMN_COMMAND", deleteColumnAction);
//		this.getInputMap().put(strokeRenameColumn, "RENAME_COLUMN_COMMAND");
//		this.getActionMap().put("RENAME_COLUMN_COMMAND", renameColumnAction);
//
//		table.getInputMap().put(strokeAddColumn, "INSERT_COLUMN_COMMAND");
//		table.getActionMap().put("INSERT_COLUMN_COMMAND", addColumnAction);
//		table.getInputMap().put(strokeRemoveColumn, "DELETE_COLUMN_COMMAND");
//		table.getActionMap().put("DELETE_COLUMN_COMMAND", deleteColumnAction);
//		table.getInputMap().put(strokeRenameColumn, "RENAME_COLUMN_COMMAND");
//		table.getActionMap().put("RENAME_COLUMN_COMMAND", renameColumnAction);
        //Move rows shortcuts
        table.getInputMap().put(moveTopPopUp.getAccelerator(), "MOVE_TOP");
        table.getActionMap().put("MOVE_TOP", moveTopAction);

        table.getInputMap().put(moveUpPopUp.getAccelerator(), "MOVE_UP");
        table.getActionMap().put("MOVE_UP", moveUpAction);

        table.getInputMap().put(moveDownPopUp.getAccelerator(), "MOVE_DOWN");
        table.getActionMap().put("MOVE_DOWN", moveDownAction);

        table.getInputMap().put(moveBottomPopUp.getAccelerator(), "MOVE_BOTTOM");
        table.getActionMap().put("MOVE_BOTTOM", moveBottomAction);

        //Move columns shortcuts
//		table.getInputMap().put(moveHomePopUp.getAccelerator(), "MOVE_HOME");
//		table.getActionMap().put("MOVE_HOME", moveHomeAction);
//
//		table.getInputMap().put(moveLeftPopUp.getAccelerator(), "MOVE_LEFT");
//		table.getActionMap().put("MOVE_LEFT", moveLeftAction);
//
//		table.getInputMap().put(moveRightPopUp.getAccelerator(), "MOVE_RIGHT");
//		table.getActionMap().put("MOVE_RIGHT", moveRightAction);
//
//		table.getInputMap().put(moveEndPopUp.getAccelerator(), "MOVE_END");
//		table.getActionMap().put("MOVE_END", moveEndAction);
        // Escape action (Because press on ESCAPE key when editing cell does not fire any event)
        // Handle escape key on a JTable
        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.isEditing()) {
                    int row = table.getEditingRow();
                    int col = table.getEditingColumn();
                    table.getCellEditor(row, col).cancelCellEditing();
                }
            }
        };
        table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(strokeEscape, "ESCAPE");
        table.getActionMap().put("ESCAPE", escapeAction);

        table.setComponentPopupMenu(tablePopUpMenu);
        tableScrollPane.setComponentPopupMenu(tablePopUpMenu);

        //Cut, Copy, Paste
        table.setTransferHandler(new TableTransferHandler());

        ActionMap map = table.getActionMap();

        map.put(TransferHandler.getCutAction().getValue(Action.NAME), TransferHandler.getCutAction());
        map.put(TransferHandler.getCopyAction().getValue(Action.NAME), TransferHandler.getCopyAction());
        map.put(TransferHandler.getPasteAction().getValue(Action.NAME), TransferHandler.getPasteAction());

        TransferActionListener ccpAction = new TransferActionListener();

        cutAction = new CCPAction(Bundle.ACTION_CUT(), ccpAction);
        cutAction.putValue(Action.ACTION_COMMAND_KEY, (String) TransferHandler.getCutAction().getValue(Action.NAME));
        cutAction.putValue(Action.ACCELERATOR_KEY, cutPopUp.getAccelerator());
        cutPopUp.setAction(cutAction);
        cutPopUp.setIcon(ImageUtilities.loadImageIcon("org/openide/resources/actions/cut.gif", false));

        copyAction = new CCPAction(Bundle.ACTION_COPY(), ccpAction);
        copyAction.putValue(Action.ACTION_COMMAND_KEY, (String) TransferHandler.getCopyAction().getValue(Action.NAME));
        copyAction.putValue(Action.ACCELERATOR_KEY, copyPopUp.getAccelerator());
        copyPopUp.setAction(copyAction);
        copyPopUp.setIcon(ImageUtilities.loadImageIcon("org/openide/resources/actions/copy.gif", false));

        pasteAction = new CCPAction(Bundle.ACTION_PASTE(), ccpAction);
        pasteAction.putValue(Action.ACTION_COMMAND_KEY, (String) TransferHandler.getPasteAction().getValue(Action.NAME));
        pasteAction.putValue(Action.ACCELERATOR_KEY, pastePopUp.getAccelerator());
        pastePopUp.setAction(pasteAction);
        pastePopUp.setIcon(ImageUtilities.loadImageIcon("org/openide/resources/actions/paste.gif", false));

        /* Integrate CCP with NetBeans default menubar items and toolbar buttons */
//		ActionMap actionMap = getActionMap();
//		actionMap.put("cut-to-clipboard", cutAction);
//		actionMap.put("copy-to-clipboard", copyAction);
//		actionMap.put("paste-from-clipboard", pasteAction);
//
//		instanceContent.add(actionMap);
        CLIPBOARD.addFlavorListener(new FlavorListener() {

            @Override
            public void flavorsChanged(FlavorEvent e) {
                final boolean dataFlavorAvailable = CLIPBOARD.isDataFlavorAvailable(TableRowTransferable.TOOLDATA_ROWS_DATA_FLAVOR);
                pasteAction.setEnabled(dataFlavorAvailable);
            }
        });

        cutAction.setEnabled(false);
        copyAction.setEnabled(false);
        pasteAction.setEnabled(false);
    }

    private void selectRow(int row) {
        Rectangle rect = table.getCellRect(row, 0, true);
        table.scrollRectToVisible(rect);
        table.clearSelection();
        table.addRowSelectionInterval(row, row);
        table.addColumnSelectionInterval(0, table.getColumnCount() - 1);
        table.requestFocusInWindow();
    }

    private void selectRowInterval(int row1, int row2) {
        Rectangle rect = table.getCellRect(row1, 0, true);
        table.scrollRectToVisible(rect);
        table.clearSelection();
        table.addRowSelectionInterval(row1, row2);
        table.addColumnSelectionInterval(0, table.getColumnCount() - 1);
        table.requestFocusInWindow();
    }

//	private void selectColumn(int column) {
//		table.clearSelection();
//		table.addColumnSelectionInterval(column, column);
//		table.addRowSelectionInterval(0, table.getRowCount() - 1);
//		table.requestFocusInWindow();
//	}
//	private void selectColumnInterval(int column1, int column2) {
//		Rectangle rect = table.getCellRect(0, column1, true);
//		table.scrollRectToVisible(rect);
//		table.clearSelection();
//		table.addColumnSelectionInterval(column1, column2);
//		table.addRowSelectionInterval(0, table.getRowCount() - 1);
//		table.requestFocusInWindow();
//	}
//    private void updateColumnsWidths() {
//        table.getTableHeader().getFont();
//        for (int i = 0; i < tableModel.getColumnCount(); i++) {
//            updateColumnWidth(i);
//        }
//    }
//
//    private HashMap<String, Integer> getColumnsWidths(TableColumnModel columnModel) {
//        HashMap<String, Integer> columnWidthHashMap = new HashMap<String, Integer>();
//        for (int j = 0; j < columnModel.getColumnCount(); j++) {
//            TableColumn tableColumn = columnModel.getColumn(j);
//            columnWidthHashMap.put((String) tableColumn.getHeaderValue(), tableColumn.getPreferredWidth());
//        }
//        return columnWidthHashMap;
//    }

//    private void setColumnsWidths(TableColumnModel columnModel, HashMap<String, Integer> columnWidthHashMap) {
//        for (int j = 0; j < columnModel.getColumnCount(); j++) {
//            final TableColumn tableColumn = columnModel.getColumn(j);
//            final String columnName = tableModel.getColumnName(j);
//            tableColumn.setHeaderValue(columnName);
//            tableColumn.setPreferredWidth(columnWidthHashMap.get(columnName));
//        }
//    }

//    private void updateColumnWidth(int colIndex) {
//        FontMetrics fontMetrics = table.getTableHeader().getFontMetrics(table.getTableHeader().getFont());
//        int width = SwingUtilities.computeStringWidth(fontMetrics, tableModel.getColumnName(colIndex));
//        for (int j = 0; j < tableModel.getRowCount(); j++) {
//            int w = SwingUtilities.computeStringWidth(fontMetrics, tableModel.getValueAt(j, colIndex));
//            if (w > width) {
//                width = w;
//            }
//        }
//        table.getColumnModel().getColumn(colIndex).setPreferredWidth(width + 25);
//    }

//	public Character getSeparator() {
//		if (separators.getSelectedIndex() == 2)
//			return '\t';
//		final String selectedItem = (String) separators.getSelectedItem();
//		return selectedItem.charAt(0);
//	}
//	public void updateSeparators() {
//		String selectedItem = (String) separators.getSelectedItem();
//		boolean newModelContainsSelected;
//		int customSeparatorCount = OptionsUtils.readCustomSeparatorCount();
//		DefaultComboBoxModel model;
//		if (customSeparatorCount > 0) {
//			List<Character> chars = OptionsUtils.readCustomSeparators(customSeparatorCount);
//			List<String> s = new ArrayList<>();
//			s.add(0, ",");
//			s.add(1, ";");
//			s.add(2, "Tab");
//			chars.stream().forEach(c -> s.add(c.toString()));
//			model = new DefaultComboBoxModel(s.toArray());
//			newModelContainsSelected = s.contains(selectedItem);
//		} else {
//			model = new DefaultComboBoxModel(new String[]{",", ";", "Tab"});
//			newModelContainsSelected = selectedItem.equals(",")
//					|| selectedItem.equals(";")
//					|| selectedItem.equals("Tab");
//		}
//		separators.setModel(model);
//		separators.setSelectedItem(selectedItem);
//		if (!newModelContainsSelected) {
//			separatorChangedAction.actionPerformed(null);
//		}
//	}
    private class CCPAction extends AbstractAction {

        private final TransferActionListener ccpAction;

        public CCPAction(String name, TransferActionListener ccpAction) {
            super(name);
            this.ccpAction = ccpAction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ActionEvent evt = new ActionEvent(table, e.getID(), (String) getValue(Action.ACTION_COMMAND_KEY), e.getWhen(), e.getModifiers());
            ccpAction.actionPerformed(evt);
        }
    }
}
