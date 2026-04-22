
/**
 * TTTFOUGSINAD
 * Class Menu serves as the UI for the inventory system and allows the player to check the status of the party.
 *
 * @author Philip Raiford
 * @version: created: 03/28/2026
 */

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Menu extends JPanel implements KeyListener, ActionListener {

  ScreenManager screenManager;

  JPanel status, itemHost, items, equipment, itemTab, equipItemsPanel;
  JPanel weapons, armor, potions, keys;

  JButton itemsWeaponTab, itemsArmorTab, itemsPotionsTab, itemsKeysTab;

  JLabel member1HpStatus, member1MpStatus, member1LevelStatus, member1ExpStatus, member1Type;
  JLabel member2HpStatus, member2MpStatus, member2LevelStatus, member2ExpStatus, member2Type;
  JLabel member3HpStatus, member3MpStatus, member3LevelStatus, member3ExpStatus, member3Type;
  JLabel member4HpStatus, member4MpStatus, member4LevelStatus, member4ExpStatus, member4Type;

  JPanel spriteHolder1, spriteHolder2, spriteHolder3, spriteHolder4;
  JLabel member1Sprite, member2Sprite, member3Sprite, member4Sprite, characterPreviewSprite;

  JPanel weaponEquipHost, armorEquipHost, characterPreviewPanel;

  JLabel weaponName, weaponDamage;
  JLabel armorName, armorDefense;

  Player[] party = getParty();

  // These are for the management of the inventory

  Border highlightedItemBorder = BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(Color.RED, 2),
      new EmptyBorder(10, 10, 10, 10));
  Border defaultItemBorder = BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(Color.BLACK),
      new EmptyBorder(10, 10, 10, 10));

  Border defaultCharacterBorder = BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(Color.BLACK),
      new EmptyBorder(10, 10, 10, 10));
  Border highlightedCharacterBorder = BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(Color.RED, 2),
      new EmptyBorder(10, 10, 10, 10));

  Border defaultButtonBorder;
  Border highlightedButtonBorder = BorderFactory.createLineBorder(Color.RED);

  int currentIndex = 0;
  int currentTab = 0;

  LinkedList<JLabel> weaponsLabels = new LinkedList<>();
  LinkedList<JLabel> armorLabels = new LinkedList<>();
  LinkedList<JLabel> potionsLabels = new LinkedList<>();
  LinkedList<JLabel> keysLabels = new LinkedList<>();

  int shiftPressed = 0;
  int rightArrowPressed = 0;
  int leftArrowPressed = 0;

  JPanel[] statusGridPanels = new JPanel[4];
  int currentCharacterSelected = 0;

  public Menu(ScreenManager screen) {

    screenManager = screen;
    //
    // JLabel label = new JLabel("This is the menu.");
    //
    // add(label);
    addKeyListener(this);
    //
    // setBackground(Color.GREEN);
    //
    setFocusable(true);

    setPreferredSize(new Dimension(1024, 768));

    setLayout(new GridLayout(2, 1));
    setBorder(new EmptyBorder(10, 10, 10, 10));

    // Holds the items and itemTab panels
    itemHost = new JPanel(new BorderLayout());
    itemHost.setBorder(new EmptyBorder(10, 10, 10, 10));

    // Holds the itemHost and equipment Panels
    equipItemsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
    equipItemsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

    // This was for testing, won't need it now
    // JLabel itemScrollListLabel = new JLabel("Items go here.");
    //
    // items.add(itemScrollListLabel);

    buildStatusPanel();
    buildEquipmentPanel();
    buildItemPanel();
    buildItemTabPanel();

    itemHost.add(itemTab, BorderLayout.NORTH);
    itemHost.add(items, BorderLayout.CENTER);

    equipItemsPanel.add(itemHost);
    equipItemsPanel.add(equipment);

    // Was testing to see how a stylized border between the different panels would
    // look
    JPanel testDecor = new JPanel();
    testDecor.setBackground(Color.RED);
    testDecor.setBorder(new EmptyBorder(10, 10, 10, 10));
    testDecor.add(new JLabel("Test Decor"));

    add(status);
    // add(testDecor);
    add(equipItemsPanel);

  }

  public void buildItemPanel() {

    // Shows a list of the player's inventory that they can sort through that is
    // tabular
    items = new JPanel(new CardLayout());
    items.setBorder(new EmptyBorder(10, 10, 10, 10));

    weapons = new JPanel();
    weapons.setLayout(new BoxLayout(weapons, BoxLayout.Y_AXIS));
    weapons.setBorder(new EmptyBorder(10, 10, 10, 10));
    armor = new JPanel();
    armor.setLayout(new BoxLayout(armor, BoxLayout.Y_AXIS));
    armor.setBorder(new EmptyBorder(10, 10, 10, 10));
    potions = new JPanel();
    potions.setLayout(new BoxLayout(potions, BoxLayout.Y_AXIS));
    potions.setBorder(new EmptyBorder(10, 10, 10, 10));
    keys = new JPanel();
    keys.setLayout(new BoxLayout(keys, BoxLayout.Y_AXIS));
    keys.setBorder(new EmptyBorder(10, 10, 10, 10));

    JScrollPane weaponScrollPane = new JScrollPane(weapons);
    JScrollPane armorScrollPane = new JScrollPane(armor);
    JScrollPane potionsScrollPane = new JScrollPane(potions);
    JScrollPane keysScrollPane = new JScrollPane(keys);

    items.add(weaponScrollPane, "weaponsTab");
    items.add(armorScrollPane, "armorTab");
    items.add(potionsScrollPane, "potionsTab");
    items.add(keysScrollPane, "keysTab");

  }

  public void buildItemTabPanel() {

    // Hosts a set of JButtons that the player can use to select what tab of the
    // inventory they want to look at
    // Need to create a cardLayout for the items JPanel to make the tabs here work
    itemTab = new JPanel(new GridLayout(1, 4, 10, 10));
    itemTab.setBorder(new EmptyBorder(10, 10, 10, 10));

    // Inventory tabs
    itemsWeaponTab = new JButton("Weapons");
    itemsWeaponTab.addActionListener(this);
    itemsWeaponTab.setFocusable(false);
    itemsArmorTab = new JButton("Armor");
    itemsArmorTab.addActionListener(this);
    itemsArmorTab.setFocusable(false);
    itemsPotionsTab = new JButton("Potions");
    itemsPotionsTab.addActionListener(this);
    itemsPotionsTab.setFocusable(false);
    itemsKeysTab = new JButton("Keys");
    itemsKeysTab.addActionListener(this);
    itemsKeysTab.setFocusable(false);

    itemTab.add(itemsWeaponTab);
    itemTab.add(itemsArmorTab);
    itemTab.add(itemsPotionsTab);
    itemTab.add(itemsKeysTab);

    defaultButtonBorder = itemsWeaponTab.getBorder();

  }

  public void buildEquipmentPanel() {

    // Shows the selected party member's equipped equipment
    equipment = new JPanel(new BorderLayout());
    // equipment.setBorder(new EmptyBorder(10, 10, 10, 10));
    equipment.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.BLACK),
        new EmptyBorder(10, 10, 10, 10)));

    // // these are leftovers from the initial build of the GUI
    // // More placeholders for the equipment slots and character preview
    // JLabel equipmentCharacterLabel = new JLabel("Character Preview",
    // SwingConstants.CENTER);
    // // JLabel equipmentHelmetLabel = new JLabel("Helmet slot",
    // // SwingConstants.CENTER);
    // JLabel equipmentWeaponLabel = new JLabel("Weapon Slot",
    // SwingConstants.CENTER);
    // JLabel equipmentArmorLabel = new JLabel("Armor Slot", SwingConstants.CENTER);
    // // JLabel equipmentBootLabel = new JLabel("Boots Slot",
    // SwingConstants.CENTER);
    //
    // equipment.add(equipmentCharacterLabel, BorderLayout.CENTER);
    // equipment.add(equipmentArmorLabel, BorderLayout.WEST);
    // // equipment.add(equipmentHelmetLabel, BorderLayout.NORTH);
    // // equipment.add(equipmentBootLabel, BorderLayout.SOUTH);
    // equipment.add(equipmentWeaponLabel, BorderLayout.EAST);

    // actual GUI work

    weaponEquipHost = new JPanel();
    weaponEquipHost.setLayout(new BoxLayout(weaponEquipHost, BoxLayout.PAGE_AXIS));
    armorEquipHost = new JPanel();
    armorEquipHost.setLayout(new BoxLayout(armorEquipHost, BoxLayout.PAGE_AXIS));

    weaponEquipHost.add(Box.createVerticalGlue());
    weaponName = new JLabel("Placeholder weapon name.");
    weaponDamage = new JLabel("Damage:");
    weaponEquipHost.add(weaponName);
    weaponEquipHost.add(weaponDamage);
    weaponEquipHost.add(Box.createVerticalGlue());

    armorEquipHost.add(Box.createVerticalGlue());
    armorName = new JLabel("Placeholder armor name.");
    armorDefense = new JLabel("Defense:");
    armorEquipHost.add(armorName);
    armorEquipHost.add(armorDefense);
    armorEquipHost.add(Box.createVerticalGlue());

    characterPreviewPanel = new JPanel();
    // characterPreviewPanel.setPreferredSize(new Dimension(200, 200));

    equipment.add(weaponEquipHost, BorderLayout.WEST);
    equipment.add(armorEquipHost, BorderLayout.EAST);
    equipment.add(characterPreviewPanel, BorderLayout.CENTER);

    characterPreviewSprite = new JLabel();

    characterPreviewPanel.add(characterPreviewSprite);

  }

  public void buildStatusPanel() {

    // Shows the party status

    status = new JPanel(new GridLayout(1, 4, 10, 10));

    // Placeholders for the character sprites and stats

    JPanel character1 = new JPanel(new GridLayout(2, 1));
    statusGridPanels[0] = character1;
    JPanel character2 = new JPanel(new GridLayout(2, 1));
    statusGridPanels[1] = character2;
    JPanel character3 = new JPanel(new GridLayout(2, 1));
    statusGridPanels[2] = character3;
    JPanel character4 = new JPanel(new GridLayout(2, 1));
    statusGridPanels[3] = character4;

    // JLabel statusLabel1 = new JLabel("Status 1", SwingConstants.CENTER);
    // JLabel statusLabel2 = new JLabel("Status 2", SwingConstants.CENTER);
    // JLabel statusLabel3 = new JLabel("Status 3", SwingConstants.CENTER);
    // JLabel statusLabel4 = new JLabel("Status 4", SwingConstants.CENTER);

    // statusGridPanels[0].add(statusLabel1);
    // statusGridPanels[1].add(statusLabel2);
    statusGridPanels[1].setBorder(defaultCharacterBorder);
    // statusGridPanels[2].add(statusLabel3);
    statusGridPanels[2].setBorder(defaultCharacterBorder);
    // statusGridPanels[3].add(statusLabel4);
    statusGridPanels[3].setBorder(defaultCharacterBorder);

    statusGridPanels[0].setBorder(highlightedCharacterBorder);

    // this section is for the specifics inside of the party section
    // it looks like a big mess, but its really just repeating the same thing 4
    // times for the different panels

    spriteHolder1 = new JPanel(new BorderLayout());
    JPanel memberStatusHolder1 = new JPanel(new GridLayout(5, 1));
    statusGridPanels[0].add(spriteHolder1);
    statusGridPanels[0].add(memberStatusHolder1);

    spriteHolder2 = new JPanel(new BorderLayout());
    JPanel memberStatusHolder2 = new JPanel(new GridLayout(5, 1));
    statusGridPanels[1].add(spriteHolder2);
    statusGridPanels[1].add(memberStatusHolder2);

    spriteHolder3 = new JPanel(new BorderLayout());
    JPanel memberStatusHolder3 = new JPanel(new GridLayout(5, 1));
    statusGridPanels[2].add(spriteHolder3);
    statusGridPanels[2].add(memberStatusHolder3);

    spriteHolder4 = new JPanel(new BorderLayout());
    JPanel memberStatusHolder4 = new JPanel(new GridLayout(5, 1));
    statusGridPanels[3].add(spriteHolder4);
    statusGridPanels[3].add(memberStatusHolder4);

    member1Type = new JLabel("Insert member type here.", SwingConstants.CENTER);
    member1LevelStatus = new JLabel("Level: ");
    member1ExpStatus = new JLabel("Exp to next level: ");
    member1HpStatus = new JLabel("HP: ");
    member1MpStatus = new JLabel("MP: ");

    member2Type = new JLabel("Insert member type here.", SwingConstants.CENTER);
    member2LevelStatus = new JLabel("Level: ");
    member2ExpStatus = new JLabel("Exp to next level: ");
    member2HpStatus = new JLabel("HP: ");
    member2MpStatus = new JLabel("MP: ");

    member3Type = new JLabel("Insert member type here.", SwingConstants.CENTER);
    member3LevelStatus = new JLabel("Level: ");
    member3ExpStatus = new JLabel("Exp to next level: ");
    member3HpStatus = new JLabel("HP: ");
    member3MpStatus = new JLabel("MP: ");

    member4Type = new JLabel("Insert member type here.", SwingConstants.CENTER);
    member4LevelStatus = new JLabel("Level: ");
    member4ExpStatus = new JLabel("Exp to next level: ");
    member4HpStatus = new JLabel("HP: ");
    member4MpStatus = new JLabel("MP: ");

    memberStatusHolder1.add(member1Type);
    memberStatusHolder1.add(member1LevelStatus);
    memberStatusHolder1.add(member1ExpStatus);
    memberStatusHolder1.add(member1HpStatus);
    memberStatusHolder1.add(member1MpStatus);

    memberStatusHolder2.add(member2Type);
    memberStatusHolder2.add(member2LevelStatus);
    memberStatusHolder2.add(member2ExpStatus);
    memberStatusHolder2.add(member2HpStatus);
    memberStatusHolder2.add(member2MpStatus);

    memberStatusHolder3.add(member3Type);
    memberStatusHolder3.add(member3LevelStatus);
    memberStatusHolder3.add(member3ExpStatus);
    memberStatusHolder3.add(member3HpStatus);
    memberStatusHolder3.add(member3MpStatus);

    memberStatusHolder4.add(member4Type);
    memberStatusHolder4.add(member4LevelStatus);
    memberStatusHolder4.add(member4ExpStatus);
    memberStatusHolder4.add(member4HpStatus);
    memberStatusHolder4.add(member4MpStatus);

    status.add(statusGridPanels[0]);
    status.add(statusGridPanels[1]);
    status.add(statusGridPanels[2]);
    status.add(statusGridPanels[3]);

    // this section here is for the party member sprites

    member1Sprite = new JLabel();
    spriteHolder1.add(member1Sprite, BorderLayout.CENTER);

    member2Sprite = new JLabel();
    spriteHolder2.add(member2Sprite, BorderLayout.CENTER);

    member3Sprite = new JLabel();
    spriteHolder3.add(member3Sprite, BorderLayout.CENTER);

    member4Sprite = new JLabel();
    spriteHolder4.add(member4Sprite, BorderLayout.CENTER);

  }

  // These four methods build what goes into each inventory tab

  public void buildWeapons() {
    weaponsLabels.clear();
    weapons.removeAll();
    for (int i = 0; i < Data.getInventory().getWeapons().size(); i++) {
      weaponsLabels.add(new JLabel(Data.getInventory().getWeapons().get(i).getName()));
      weaponsLabels.get(i).setFont(weaponsLabels.get(i).getFont().deriveFont(24f));
      weaponsLabels.get(i)
          .setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) weaponsLabels.get(i).getPreferredSize().getHeight()));
      weaponsLabels.get(i).setBorder(defaultItemBorder);
      weapons.add(weaponsLabels.get(i));
      weapons.add(Box.createRigidArea(new Dimension(0, 10)));
    }
    weapons.revalidate();
    reHighlight();
  }

  public void buildArmor() {
    armorLabels.clear();
    armor.removeAll();
    for (int i = 0; i < Data.getInventory().getArmor().size(); i++) {
      armorLabels.add(new JLabel(Data.getInventory().getArmor().get(i).getName()));
      armorLabels.get(i).setFont(armorLabels.get(i).getFont().deriveFont(24f));
      armorLabels.get(i)
          .setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) armorLabels.get(i).getPreferredSize().getHeight()));
      armorLabels.get(i).setBorder(defaultItemBorder);
      armor.add(armorLabels.get(i));
      armor.add(Box.createRigidArea(new Dimension(0, 10)));
    }
    armor.revalidate();
    reHighlight();
  }

  public void buildPotions() {
    potionsLabels.clear();
    potions.removeAll();
    for (int i = 0; i < Data.getInventory().getPotions().size(); i++) {
      potionsLabels.add(new JLabel(Data.getInventory().getPotions().get(i).getName()));
      potionsLabels.get(i).setFont(potionsLabels.get(i).getFont().deriveFont(24f));
      potionsLabels.get(i)
          .setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) potionsLabels.get(i).getPreferredSize().getHeight()));
      potionsLabels.get(i).setBorder(defaultItemBorder);
      potions.add(potionsLabels.get(i));
      potions.add(Box.createRigidArea(new Dimension(0, 10)));
    }
    potions.revalidate();
    reHighlight();
  }

  public void buildKeys() {
    keysLabels.clear();
    keys.removeAll();
    for (int i = 0; i < Data.getInventory().getKeys().size(); i++) {
      keysLabels.add(new JLabel(Data.getInventory().getKeys().get(i).getName()));
      keysLabels.get(i).setFont(keysLabels.get(i).getFont().deriveFont(24f));
      keysLabels.get(i)
          .setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) keysLabels.get(i).getPreferredSize().getHeight()));
      keysLabels.get(i).setBorder(defaultItemBorder);
      keys.add(keysLabels.get(i));
      keys.add(Box.createRigidArea(new Dimension(0, 10)));
    }
    keys.revalidate();
    reHighlight();
  }

  public void buildInventory() {
    buildWeapons();
    buildArmor();
    buildPotions();
    buildKeys();
  }

  // these three methods build the character images for the menu and mostly scale
  // them correctly
  public void buildStatusSprites() {

    member1Sprite.setIcon(scaleIcon(party[0].downSprite1, spriteHolder1.getWidth(),
        spriteHolder1.getHeight()));
    member2Sprite.setIcon(scaleIcon(party[1].rightSprite1, spriteHolder2.getWidth(),
        spriteHolder2.getHeight()));
    member3Sprite.setIcon(scaleIcon(party[2].upSprite1, spriteHolder3.getWidth(),
        spriteHolder3.getHeight()));
    member4Sprite.setIcon(scaleIcon(party[3].leftSprite1, spriteHolder4.getWidth(),
        spriteHolder4.getHeight()));

  }

  public void buildPreviewSprite() {

    // System.out
    // .println("Preview Panel size: " + characterPreviewPanel.getWidth() + ", " +
    // characterPreviewPanel.getHeight());
    characterPreviewSprite.setIcon(scaleIcon(party[currentCharacterSelected].downSprite1,
        characterPreviewPanel.getWidth(), characterPreviewPanel.getHeight()));

  }

  public ImageIcon scaleIcon(ImageIcon image, int w, int h) {

    if (image == null) {
      System.out.println("Image is null! Returning.");
      return null;
    }

    Image scale = image.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
    return new ImageIcon(scale);

  }

  public void itemTabSwitch(String button) {

    CardLayout layout = (CardLayout) items.getLayout();
    System.out.println("Attempting to switch inventory tab");
    itemsWeaponTab.setBorder(defaultButtonBorder);
    itemsArmorTab.setBorder(defaultButtonBorder);
    itemsPotionsTab.setBorder(defaultButtonBorder);
    itemsKeysTab.setBorder(defaultButtonBorder);
    if (button.equals("Weapons")) {
      layout.show(items, "weaponsTab");
      itemsWeaponTab.setBorder(highlightedButtonBorder);
    } else if (button.equals("Armor")) {
      layout.show(items, "armorTab");
      itemsArmorTab.setBorder(highlightedButtonBorder);
    } else if (button.equals("Potions")) {
      layout.show(items, "potionsTab");
      itemsPotionsTab.setBorder(highlightedButtonBorder);
    } else if (button.equals("Keys")) {
      layout.show(items, "keysTab");
      itemsKeysTab.setBorder(highlightedButtonBorder);
    } else {
      System.out.println("Failed to switch inventory tab");
    }

  }

  public void actionPerformed(ActionEvent e) {

    String action = e.getActionCommand();
    unHighlight();
    if (action.equals("Weapons")) {
      currentTab = 0;
    } else if (action.equals("Armor")) {
      currentTab = 1;
    } else if (action.equals("Potions")) {
      currentTab = 2;
    } else if (action.equals("Keys")) {
      currentTab = 3;
    } else {
      System.out.println("Failed to set currentTab based on button switch!");
    }
    currentIndex = 0;
    itemTabSwitch(action);
    reHighlight();

  }

  public void switchToMenu() {
    requestFocusInWindow();
    currentIndex = 0;
    currentTab = 0;
    currentCharacterSelected = 0;
    buildInventory();
    buildPartyMemberInfo();
    reHighlight();
    statusGridPanels[currentCharacterSelected].setBorder(highlightedCharacterBorder);
    weaponName.setText(party[currentCharacterSelected].getWeaponEquip().getName());
    weaponDamage.setText("Damage: " + party[currentCharacterSelected].getWeaponEquip().getDamage());
    armorName.setText(party[currentCharacterSelected].getArmorEquip().getName());
    armorDefense.setText("Defense: " + party[currentCharacterSelected].getArmorEquip().getDamage());
    buildStatusSprites();
    buildPreviewSprite();
    itemTabSwitch("Weapons");
  }

  // unselects the selected item becuase otherwise when exiting and returning to
  // the menu it would remember where you were
  public void exitMenu() {
    if (currentTab == 0) {
      weaponsLabels.get(currentIndex).setBorder(defaultItemBorder);
      weaponsLabels.get(currentIndex).setForeground(Color.BLACK);
      weapons.revalidate();
    } else if (currentTab == 1) {
      armorLabels.get(currentIndex).setBorder(defaultItemBorder);
      armorLabels.get(currentIndex).setForeground(Color.BLACK);
      armor.revalidate();
    } else if (currentTab == 2) {
      potionsLabels.get(currentIndex).setBorder(defaultItemBorder);
      potionsLabels.get(currentIndex).setForeground(Color.BLACK);
      potions.revalidate();
    } else if (currentTab == 3) {
      keysLabels.get(currentIndex).setBorder(defaultItemBorder);
      keysLabels.get(currentIndex).setForeground(Color.BLACK);
      keys.revalidate();
    }
    statusGridPanels[currentCharacterSelected].setBorder(defaultItemBorder);
    currentCharacterSelected = 0;
  }

  public void keyPressed(KeyEvent e) {

    // This is for tracking to see if the combination keys have been pressed
    if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
      shiftPressed = 1;
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      rightArrowPressed = 1;
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      leftArrowPressed = 1;
    }

    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // These ones are for testing the ScreenManager screen switching
      System.out.println("Attempting to switch to Map.");
      exitMenu();
      screenManager.switchTo("Map");
    } else if (e.getKeyCode() == KeyEvent.VK_W) { // These ones here are for testing inventory adding
      Data.getInventory().addTestWeapon();
    } else if (e.getKeyCode() == KeyEvent.VK_A) {
      Data.getInventory().addTestArmor();
    } else if (e.getKeyCode() == KeyEvent.VK_P) {
      Data.getInventory().addTestPotion();
    } else if (e.getKeyCode() == KeyEvent.VK_K) {
      Data.getInventory().addTestKey();
    }

    // Everything from here on is for player management of the invenotry

    if (e.getKeyCode() == KeyEvent.VK_UP) {
      System.out.println("Up key pressed.");
      if (currentIndex == 0) {
        System.out.println("Out of bounds for the LinkedList.");
        return;
      }
      currentIndex--;
      reHighlight();
      unHighlightUp();
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      System.out.println("Down key pressed.");
      System.out.println("currentIndex = " + currentIndex);
      if (currentTab == 0 && Data.getInventory().getWeapons().size() - 1 <= currentIndex) {
        System.out.println("Out of bounds for the LinkedList.");
        return;
      } else if (currentTab == 1 && Data.getInventory().getArmor().size() - 1 <= currentIndex) {
        System.out.println("Out of bounds for the LinkedList");
        return;
      } else if (currentTab == 2 && Data.getInventory().getPotions().size() - 1 <= currentIndex) {
        System.out.println("Out of bounds for the LinkedList.");
        return;
      } else if (currentTab == 3 && Data.getInventory().getKeys().size() - 1 <= currentIndex) {
        System.out.println("Out of bounds for the LinkedList.");
        return;
      }
      currentIndex++;
      reHighlight();
      unHighlightDown();
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && shiftPressed == 0) {
      System.out.println("Attempting to move tab to the right.");
      if (currentTab == 3) {
        System.out.println("Already in rightmost tab, canelling.");
        return;
      }
      unHighlight();
      currentTab++;
      if (currentTab == 1) {
        itemTabSwitch("Armor");
      } else if (currentTab == 2) {
        itemTabSwitch("Potions");
      } else if (currentTab == 3) {
        itemTabSwitch("Keys");
      }
      currentIndex = 0;
      reHighlight();
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT && shiftPressed == 0) {
      System.out.println("Attempting to move the tab to the left.");
      if (currentTab == 0) {
        System.out.println("Already in the leftmost tab, cancelling.");
        return;
      }
      unHighlight();
      currentTab--;
      if (currentTab == 0) {
        itemTabSwitch("Weapons");
      } else if (currentTab == 1) {
        itemTabSwitch("Armor");
      } else if (currentTab == 2) {
        itemTabSwitch("Potions");
      }
      currentIndex = 0;
      reHighlight();
    }

    // This section is for the selection of the character, uses a combination of
    // keys as the input

    if (shiftPressed == 1 && rightArrowPressed == 1) {

      if (currentCharacterSelected == 3) {
        System.out.println("Current character selected is already the righmost one, exiting.");
        return;
      }
      currentCharacterSelected++;
      statusGridPanels[currentCharacterSelected].setBorder(highlightedCharacterBorder);
      statusGridPanels[currentCharacterSelected - 1].setBorder(defaultCharacterBorder);

      weaponName.setText(party[currentCharacterSelected].getWeaponEquip().getName());
      weaponDamage.setText("Damage: " + party[currentCharacterSelected].getWeaponEquip().getDamage());

      armorName.setText(party[currentCharacterSelected].getArmorEquip().getName());
      armorDefense.setText("Defense: " + party[currentCharacterSelected].getArmorEquip().getDamage());

      buildPreviewSprite();

    } else if (shiftPressed == 1 && leftArrowPressed == 1) {

      if (currentCharacterSelected == 0) {
        System.out.println("Current character selected is already the leftmost one, exiting.");
        return;
      }
      currentCharacterSelected--;
      statusGridPanels[currentCharacterSelected].setBorder(highlightedCharacterBorder);
      statusGridPanels[currentCharacterSelected + 1].setBorder(defaultCharacterBorder);

      weaponName.setText(party[currentCharacterSelected].getWeaponEquip().getName());
      weaponDamage.setText("Damage: " + party[currentCharacterSelected].getWeaponEquip().getDamage());

      armorName.setText(party[currentCharacterSelected].getArmorEquip().getName());
      armorDefense.setText("Defense: " + party[currentCharacterSelected].getArmorEquip().getDamage());

      buildPreviewSprite();

    }

    if (e.getKeyCode() == KeyEvent.VK_ENTER) {

      if (currentTab == 0) { // this is for the weapons

        // prevents the player from crashing the program by equipping nothing
        if (weaponsLabels.isEmpty()) {
          System.out.println("Weapons labels is empty, cannot equip anything, returning.");
          return;
        }

        Item equippedWeapon = party[currentCharacterSelected].getWeaponEquip();
        Item weaponToEquip = Data.getInventory().getWeapons().get(currentIndex);

        party[currentCharacterSelected].setWeaponEquip(weaponToEquip);
        Data.getInventory().getWeapons().remove(currentIndex);
        Data.addWeapon(currentIndex, equippedWeapon.getName(), equippedWeapon.getDamage());

        weaponName.setText(party[currentCharacterSelected].getWeaponEquip().getName());
        weaponDamage.setText("Damage: " + party[currentCharacterSelected].getWeaponEquip().getDamage());

      } else if (currentTab == 1) { // This is for the armor

        // prevents the player from crashing the program by equipping nothing
        if (armorLabels.isEmpty()) {
          System.out.println("Armor labels is empty, cannot equip anything, returning.");
          return;
        }

        Item equippedArmor = party[currentCharacterSelected].getArmorEquip();
        Item armorToEquip = Data.getInventory().getArmor().get(currentIndex);

        party[currentCharacterSelected].setArmorEquip(armorToEquip);
        Data.getInventory().getArmor().remove(currentIndex);
        Data.addArmor(currentIndex, equippedArmor.getName(), equippedArmor.getDamage());

        armorName.setText(party[currentCharacterSelected].getArmorEquip().getName());
        armorDefense.setText("Defense: " + party[currentCharacterSelected].getArmorEquip().getDamage());

      } else if (currentTab == 2) { // this is for the potions

        // prevents the player from using a potion they do not have
        if (potionsLabels.isEmpty()) {
          System.out.println("Potion labels is empty, returning.");
          return;
        }

        if (party[currentCharacterSelected].hp >= party[currentCharacterSelected].maxHp) {
          System.out.println("The selected party member's hp is already full! retunring.");
          return;
        }

        Item potionToUse = Data.getInventory().getPotions().get(currentIndex);

        party[currentCharacterSelected].usePotion(potionToUse);

        Data.getInventory().getPotions().remove(currentIndex);

        if (currentIndex > 0 && currentIndex >= Data.getInventory().getPotions().size()) {
          currentIndex--;
        }

        buildPotions();
        buildPartyMemberInfo();
        repaint();

      } else if (currentTab == 3) { // this is for the keys, but they dont do anything right now, leaving blank

      }

    }

  }

  public void keyTyped(KeyEvent e) {

  }

  public void keyReleased(KeyEvent e) {

    if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
      shiftPressed = 0;
    }

    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      rightArrowPressed = 0;
    }

    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      leftArrowPressed = 0;
    }

  }

  // this section is for pulling in the character data for displaying their stats
  // and equipment

  public Player[] getParty() {
    return Party.getParty();
  }

  public void buildPartyMemberInfo() {

    member1Type.setText(party[0].playerType);
    member1LevelStatus.setText("Level: " + party[0].level);
    member1ExpStatus.setText("Exp to next level: " + (100 - party[0].playerXp));
    member1HpStatus.setText("HP: " + party[0].hp);
    member1MpStatus.setText("MP: " + party[0].mp);

    member2Type.setText(party[1].playerType);
    member2LevelStatus.setText("Level: " + party[1].level);
    member2ExpStatus.setText("Exp to next level: " + (100 - party[1].playerXp));
    member2HpStatus.setText("HP: " + party[1].hp);
    member2MpStatus.setText("MP: " + party[1].mp);

    member3Type.setText(party[2].playerType);
    member3LevelStatus.setText("Level: " + party[2].level);
    member3ExpStatus.setText("Exp to next level: " + (100 - party[2].playerXp));
    member3HpStatus.setText("HP: " + party[2].hp);
    member3MpStatus.setText("MP: " + party[2].mp);

    member4Type.setText(party[3].playerType);
    member4LevelStatus.setText("Level: " + party[3].level);
    member4ExpStatus.setText("Exp to next level: " + (100 - party[3].playerXp));
    member4HpStatus.setText("HP: " + party[3].hp);
    member4MpStatus.setText("MP: " + party[3].mp);

  }

  // these methods are for the player's control over the inventory

  public void reHighlight() {

    // this section here is to prevent crashing in case the inventory is empty
    if (currentTab == 0 && weaponsLabels.isEmpty()) {
      System.out.println("Weapons labels is empty, returning.");
      return;
    } else if (currentTab == 1 && armorLabels.isEmpty()) {
      System.out.println("Armor labels is empty, returning.");
      return;
    } else if (currentTab == 2 && potionsLabels.isEmpty()) {
      System.out.println("Potions labels is empty, returning.");
      return;
    } else if (currentTab == 3 && keysLabels.isEmpty()) {
      System.out.println("Keys labels is empty, returning.");
      return;
    }

    if (currentTab == 0) {
      weaponsLabels.get(currentIndex).setBorder(highlightedItemBorder);
      weaponsLabels.get(currentIndex).setForeground(Color.RED);
      weapons.revalidate();
    } else if (currentTab == 1) {
      armorLabels.get(currentIndex).setBorder(highlightedItemBorder);
      armorLabels.get(currentIndex).setForeground(Color.RED);
      armor.revalidate();
    } else if (currentTab == 2) {
      potionsLabels.get(currentIndex).setBorder(highlightedItemBorder);
      potionsLabels.get(currentIndex).setForeground(Color.RED);
      potions.revalidate();
    } else if (currentTab == 3) {
      keysLabels.get(currentIndex).setBorder(highlightedItemBorder);
      keysLabels.get(currentIndex).setForeground(Color.RED);
      keys.revalidate();
    }
  }

  public void unHighlight() {

    if (currentTab == 0 && weaponsLabels.isEmpty()) {
      System.out.println("Weapons labels is empty, returning.");
      return;
    } else if (currentTab == 1 && armorLabels.isEmpty()) {
      System.out.println("Armor labels is empty, returning.");
      return;
    } else if (currentTab == 2 && potionsLabels.isEmpty()) {
      System.out.println("Potions labels is empty, returning.");
      return;
    } else if (currentTab == 3 && keysLabels.isEmpty()) {
      System.out.println("Keys labels is empty, returning.");
      return;
    }

    if (currentTab == 0) {
      weaponsLabels.get(currentIndex).setBorder(defaultItemBorder);
      weaponsLabels.get(currentIndex).setForeground(Color.BLACK);
      weapons.revalidate();
    } else if (currentTab == 1) {
      armorLabels.get(currentIndex).setBorder(defaultItemBorder);
      armorLabels.get(currentIndex).setForeground(Color.BLACK);
      armor.revalidate();
    } else if (currentTab == 2) {
      potionsLabels.get(currentIndex).setBorder(defaultItemBorder);
      potionsLabels.get(currentIndex).setForeground(Color.BLACK);
      potions.revalidate();
    } else if (currentTab == 3) {
      keysLabels.get(currentIndex).setBorder(defaultItemBorder);
      keysLabels.get(currentIndex).setForeground(Color.BLACK);
      keys.revalidate();
    }
  }

  // Use this one when the player want to move the highlighted item up one,
  // meaning that this method unhighlights the one below the new selected item

  public void unHighlightUp() {

    if (currentTab == 0 && weaponsLabels.isEmpty()) {
      System.out.println("Weapons labels is empty, returning.");
      return;
    } else if (currentTab == 1 && armorLabels.isEmpty()) {
      System.out.println("Armor labels is empty, returning.");
      return;
    } else if (currentTab == 2 && potionsLabels.isEmpty()) {
      System.out.println("Potions labels is empty, returning.");
      return;
    } else if (currentTab == 3 && keysLabels.isEmpty()) {
      System.out.println("Keys labels is empty, returning.");
      return;
    }

    if (currentTab == 0) {
      weaponsLabels.get(currentIndex + 1).setBorder(defaultItemBorder);
      weaponsLabels.get(currentIndex + 1).setForeground(Color.BLACK);
      weapons.revalidate();
    } else if (currentTab == 1) {
      armorLabels.get(currentIndex + 1).setBorder(defaultItemBorder);
      armorLabels.get(currentIndex + 1).setForeground(Color.BLACK);
      armor.revalidate();
    } else if (currentTab == 2) {
      potionsLabels.get(currentIndex + 1).setBorder(defaultItemBorder);
      potionsLabels.get(currentIndex + 1).setForeground(Color.BLACK);
      potions.revalidate();
    } else if (currentTab == 3) {
      keysLabels.get(currentIndex + 1).setBorder(defaultItemBorder);
      keysLabels.get(currentIndex + 1).setForeground(Color.BLACK);
      keys.revalidate();
    }
  }

  // the opposite of the one above

  public void unHighlightDown() {

    if (currentTab == 0 && weaponsLabels.isEmpty()) {
      System.out.println("Weapons labels is empty, returning.");
      return;
    } else if (currentTab == 1 && armorLabels.isEmpty()) {
      System.out.println("Armor labels is empty, returning.");
      return;
    } else if (currentTab == 2 && potionsLabels.isEmpty()) {
      System.out.println("Potions labels is empty, returning.");
      return;
    } else if (currentTab == 3 && keysLabels.isEmpty()) {
      System.out.println("Keys labels is empty, returning.");
      return;
    }

    if (currentTab == 0) {
      weaponsLabels.get(currentIndex - 1).setBorder(defaultItemBorder);
      weaponsLabels.get(currentIndex - 1).setForeground(Color.BLACK);
      weapons.revalidate();
    } else if (currentTab == 1) {
      armorLabels.get(currentIndex - 1).setBorder(defaultItemBorder);
      armorLabels.get(currentIndex - 1).setForeground(Color.BLACK);
      armor.revalidate();
    } else if (currentTab == 2) {
      potionsLabels.get(currentIndex - 1).setBorder(defaultItemBorder);
      potionsLabels.get(currentIndex - 1).setForeground(Color.BLACK);
      potions.revalidate();
    } else if (currentTab == 3) {
      keysLabels.get(currentIndex - 1).setBorder(defaultItemBorder);
      keysLabels.get(currentIndex - 1).setForeground(Color.BLACK);
      keys.revalidate();
    }
  }

  //
  // public void highlightButtonTab() {
  //
  // if (currentTab == 0) {
  // itemsWeaponTab.setBorder(highlightedButtonBorder);
  // } else if (currentTab == 1) {
  // itemsArmorTab.setBorder(highlightedButtonBorder);
  // } else if (currentTab == 2) {
  // itemsPotionsTab.setBorder(highlightedButtonBorder);
  // } else if (currentTab == 3) {
  // itemsKeysTab.setBorder(highlightedButtonBorder);
  // } else {
  // System.out.println("Failed to highlight the item tab.");
  // }
  //
  // }
  //
  // public void unHighlightButtonTab() {
  //
  // if (currentTab == 0) {
  // itemsWeaponTab.setBorder(defaultButtonBorder);
  // } else if (currentTab == 1) {
  // itemsArmorTab.setBorder(defaultButtonBorder);
  // } else if (currentTab == 2) {
  // itemsPotionsTab.setBorder(defaultButtonBorder);
  // } else if (currentTab == 3) {
  // itemsKeysTab.setBorder(defaultButtonBorder);
  // } else {
  // System.out.println("Failed to unhighlight the item tab.");
  // }
  //
  // }
  //
  public LinkedList<JLabel> getPotionsLabels() {
    return potionsLabels;
  }

}
