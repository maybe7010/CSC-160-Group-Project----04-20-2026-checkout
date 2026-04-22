
/**
 * TTTFOUGSINAD
 * Class Battle contains the battle ui for the game and the functions for the battle system to work.
 *
 * @author Philip Raiford
 * @author Loren Wolf
 * @version created: 03/27/2026
 */

import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.event.*;
import java.util.*;

import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.border.*;

public class Battle extends JPanel implements KeyListener, ActionListener {

  ScreenManager screenManager;

  JPanel entityBoard, moveHost, moves, skills, skillItemHost;
  JPanel partyMember1Skills, partyMember2Skills, partyMember3Skills, partyMember4Skills, potions;
  JPanel attackPanel, otherMovesPanel;

  JButton attackButton, guardButton, skillsButton, runButton, itemsButton;

  Border defaultSkillsBorder = BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(Color.BLACK),
      new EmptyBorder(5, 5, 5, 5));
  Border highlightedSkillsBorder = BorderFactory.createCompoundBorder(
      BorderFactory.createLineBorder(Color.RED, 2),
      new EmptyBorder(5, 5, 5, 5));
  Border blankEntityBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5); // This section is for party management

  LinkedList<JLabel> partyMember1SkillsLabels = new LinkedList<>();
  LinkedList<JLabel> partyMember2SkillsLabels = new LinkedList<>();
  LinkedList<JLabel> partyMember3SkillsLabels = new LinkedList<>();
  LinkedList<JLabel> partyMember4SkillsLabels = new LinkedList<>();

  int currentPartyMember = 0;
  int currentTab;
  int currentIndex = 0;

  boolean playerTurn = true;

  JPanel[][] enemyPartyLayout = new JPanel[2][4];
  int selectedEnemy = 0;

  // This section is for potions management

  LinkedList<JLabel> potionsLabels = new LinkedList<>();

  public Battle(ScreenManager screen) {

    screenManager = screen;

    setLayout(new GridBagLayout());

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weightx = 1;
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.weighty = 0.65;

    addKeyListener(this);

    buildEntityBoard();
    buildMoves();
    buildSkills();
    buildSkillItemHost();
    buildMovesHost();

    add(entityBoard, constraints);

    constraints.gridy = 1;
    constraints.weighty = 0.35;

    add(moveHost, constraints);

    setPreferredSize(new Dimension(1024, 768));

    setFocusable(true);

  }

  public void buildEntityBoard() {

    entityBoard = new JPanel(new GridLayout(2, 4));
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 4; j++) {
        enemyPartyLayout[i][j] = new JPanel(new BorderLayout());
        entityBoard.add(enemyPartyLayout[i][j]);
      }
    }

    enemyPartyLayout[0][0].add(new JLabel("Enemy 4"));
    enemyPartyLayout[0][0].setBorder(blankEntityBorder);
    enemyPartyLayout[0][1].add(new JLabel("Enemy 3"));
    enemyPartyLayout[0][1].setBorder(blankEntityBorder);
    enemyPartyLayout[0][2].add(new JLabel("Enemy 2"));
    enemyPartyLayout[0][2].setBorder(blankEntityBorder);
    enemyPartyLayout[0][3].add(new JLabel("Enemy 1"));
    enemyPartyLayout[0][3].setBorder(blankEntityBorder);

    enemyPartyLayout[1][0].add(new JLabel("Party Member 1"));
    enemyPartyLayout[1][0].setBorder(blankEntityBorder);
    enemyPartyLayout[1][1].add(new JLabel("Party Member 2"));
    enemyPartyLayout[1][1].setBorder(blankEntityBorder);
    enemyPartyLayout[1][2].add(new JLabel("Party Member 3"));
    enemyPartyLayout[1][2].setBorder(blankEntityBorder);
    enemyPartyLayout[1][3].add(new JLabel("Party Member 4"));
    enemyPartyLayout[1][3].setBorder(blankEntityBorder);

  }

  public void buildMovesHost() {

    moveHost = new JPanel(new GridBagLayout());
    moveHost.setBorder(defaultSkillsBorder);

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weighty = 1;
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.weightx = 0.6;

    moveHost.add(skillItemHost, constraints);

    constraints.weightx = 0.4;
    constraints.gridx = 1;

    moveHost.add(moves, constraints);

  }

  // skillItemHost exists as a way to host the party skills and potions/items in
  // different spots in the UI, otherwise it does nothing else, this method also
  // builds the potions tab
  public void buildSkillItemHost() {

    skillItemHost = new JPanel(new CardLayout());

    potions = new JPanel();
    potions.setLayout(new BoxLayout(potions, BoxLayout.Y_AXIS));
    potions.setBorder(new EmptyBorder(10, 10, 10, 10));

    JScrollPane potionsScrollPane = new JScrollPane(potions);

    skillItemHost.add(skills, "skills");
    skillItemHost.add(potionsScrollPane, "potions");

  }

  public void buildSkills() {

    // This builds the panels necessary to display the skills of the party members

    skills = new JPanel(new CardLayout());

    partyMember1Skills = new JPanel();
    partyMember1Skills.setLayout(new BoxLayout(partyMember1Skills, BoxLayout.Y_AXIS));
    partyMember1Skills.setBorder(new EmptyBorder(10, 10, 10, 10));

    partyMember2Skills = new JPanel();
    partyMember2Skills.setLayout(new BoxLayout(partyMember2Skills, BoxLayout.Y_AXIS));
    partyMember2Skills.setBorder(new EmptyBorder(10, 10, 10, 10));

    partyMember3Skills = new JPanel();
    partyMember3Skills.setLayout(new BoxLayout(partyMember3Skills, BoxLayout.Y_AXIS));
    partyMember3Skills.setBorder(new EmptyBorder(10, 10, 10, 10));

    partyMember4Skills = new JPanel();
    partyMember4Skills.setLayout(new BoxLayout(partyMember4Skills, BoxLayout.Y_AXIS));
    partyMember4Skills.setBorder(new EmptyBorder(10, 10, 10, 10));

    JScrollPane partyMember1SkillsScrollPane = new JScrollPane(partyMember1Skills);
    JScrollPane partyMember2SkillsScrollPane = new JScrollPane(partyMember2Skills);
    JScrollPane partyMember3SkillsScrollPane = new JScrollPane(partyMember3Skills);
    JScrollPane partyMember4SkillsScrollPane = new JScrollPane(partyMember4Skills);

    skills.add(partyMember1SkillsScrollPane, "partyMember1");
    skills.add(partyMember2SkillsScrollPane, "partyMember2");
    skills.add(partyMember3SkillsScrollPane, "partyMember3");
    skills.add(partyMember4SkillsScrollPane, "partyMember4");

  }

  public void buildMoves() {

    moves = new JPanel(new GridLayout(2, 1));

    JPanel attackPanel = new JPanel(new GridLayout(1, 2));
    JPanel otherMovesPanel = new JPanel(new GridLayout(1, 3));

    attackButton = new JButton("Attack");
    attackButton.addActionListener(this);
    attackButton.setFocusable(false);

    skillsButton = new JButton("Skills");
    skillsButton.addActionListener(this);
    skillsButton.setFocusable(false);

    guardButton = new JButton("Guard");
    guardButton.addActionListener(this);
    guardButton.setFocusable(false);

    runButton = new JButton("Run");
    runButton.addActionListener(this);
    runButton.setFocusable(false);

    itemsButton = new JButton("Potions");
    itemsButton.addActionListener(this);
    itemsButton.setFocusable(false);

    attackPanel.add(attackButton);
    attackPanel.add(skillsButton);
    otherMovesPanel.add(itemsButton);
    otherMovesPanel.add(guardButton);
    otherMovesPanel.add(runButton);

    moves.add(attackPanel);
    moves.add(otherMovesPanel);

  }

  // this is temporary until the entity and party system is figured out, this
  // exists for UI testing
  public void buildTestSkills() {

    partyMember1SkillsLabels.add(new JLabel("Party Member 1 Skills"));
    partyMember1SkillsLabels.add(new JLabel("Skill 1"));

    partyMember2SkillsLabels.add(new JLabel("Party Member 2 Skills"));
    partyMember2SkillsLabels.add(new JLabel("Skill 2"));

    partyMember3SkillsLabels.add(new JLabel("Party Member 3 Skills"));
    partyMember3SkillsLabels.add(new JLabel("Skill 3 "));

    partyMember4SkillsLabels.add(new JLabel("Party Member 4 Skills"));
    partyMember4SkillsLabels.add(new JLabel("Skill 4"));

  }

  public void buildPartyMember1Skills() {

    // add this one back only when the party skills are ready to be pulled
    // partyMember1SkillsLabels.clear();
    partyMember1Skills.removeAll();
    for (int i = 0; i < partyMember1SkillsLabels.size(); i++) {
      partyMember1Skills.add(partyMember1SkillsLabels.get(i));
      partyMember1SkillsLabels.get(i)
          .setMaximumSize(
              new Dimension(Integer.MAX_VALUE, (int) partyMember1SkillsLabels.get(i).getPreferredSize().getHeight()));
    }
    partyMember1Skills.revalidate();
    reHighlight();

  }

  public void buildPartyMember2Skills() {

    // add this one back only when the party skills are ready to be pulled
    // partyMember2SkillsLabels.clear();
    partyMember2Skills.removeAll();
    for (int i = 0; i < partyMember2SkillsLabels.size(); i++) {
      partyMember2Skills.add(partyMember2SkillsLabels.get(i));
      partyMember2SkillsLabels.get(i)
          .setMaximumSize(
              new Dimension(Integer.MAX_VALUE, (int) partyMember2SkillsLabels.get(i).getPreferredSize().getHeight()));
    }
    partyMember2Skills.revalidate();
    reHighlight();

  }

  public void buildPartyMember3Skills() {

    // add this one back only when the party skills are ready to be pulled
    // partyMember3SkillsLabels.clear();
    partyMember3Skills.removeAll();
    for (int i = 0; i < partyMember3SkillsLabels.size(); i++) {
      partyMember3Skills.add(partyMember3SkillsLabels.get(i));
      partyMember3SkillsLabels.get(i)
          .setMaximumSize(
              new Dimension(Integer.MAX_VALUE, (int) partyMember3SkillsLabels.get(i).getPreferredSize().getHeight()));
    }
    partyMember3Skills.revalidate();
    reHighlight();

  }

  public void buildPartyMember4Skills() {

    // add this one back only when the party skills are ready to be pulled
    // partyMember4SkillsLabels.clear();
    partyMember4Skills.removeAll();
    for (int i = 0; i < partyMember4SkillsLabels.size(); i++) {
      partyMember4Skills.add(partyMember4SkillsLabels.get(i));
      partyMember4SkillsLabels.get(i)
          .setMaximumSize(
              new Dimension(Integer.MAX_VALUE, (int) partyMember4SkillsLabels.get(i).getPreferredSize().getHeight()));
    }
    partyMember4Skills.revalidate();
    reHighlight();

  }

  public void buildPotions() {

    potions.removeAll();
    for (int i = 0; i < potionsLabels.size(); i++) {
      potions.add(potionsLabels.get(i));
      potionsLabels.get(i)
          .setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) potionsLabels.get(i).getPreferredSize().getHeight()));
    }
    potions.revalidate();
    reHighlight();

  }

  public void buildAllMoves() {

    buildPartyMember1Skills();
    buildPartyMember2Skills();
    buildPartyMember3Skills();
    buildPartyMember4Skills();
    buildPotions();

  }

  public void generatePotionsLabels() {

    potionsLabels.clear();
    for (int i = 0; i < ScreenManager.menu.potionsLabels.size(); i++) {
      JLabel p = new JLabel(Data.getInventory().getPotions().get(i).getName());
      p.setBorder(defaultSkillsBorder);
      p.setForeground(Color.BLACK);
      p.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) p.getPreferredSize().getHeight()));
      potionsLabels.add(p);
    }
    buildPotions();

  }

  public void switchToBattle() {
    requestFocusInWindow();
    generatePotionsLabels();

    currentIndex = 0;
    currentTab = currentPartyMember;

    unHighlightAll();
    selectedEnemy = 3;
    enemyPartyLayout[0][3].setBorder(highlightedSkillsBorder);

    buildPartyMember1Skills();
    buildPartyMember2Skills();
    buildPartyMember3Skills();
    buildPartyMember4Skills();
    generatePotionsLabels();
    buildPotions();

    reHighlight();
  }

  public void exitBattle() {
    unHighlightAll();
  }

  public void keyPressed(KeyEvent e) {

    if (playerTurn == false) {
      System.out.println("Not your turn.");
      return;
    }

    if (e.getKeyCode() == KeyEvent.VK_M) {
      System.out.println("Attempting to switch to Map.");
      exitBattle();
      screenManager.switchTo("Map");
    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      /*
       * System.out.println("Attempting to switch to Menu.");
       * exitBattle();
       * screenManager.switchTo("Menu");
       */
    }

    // This section is for party skills management

    if (e.getKeyCode() == KeyEvent.VK_UP) {

      System.out.println("Up key pressed");
      if (currentIndex == 0) {
        System.out.println("Already at the upmost position, returning.");
        return;
      }

      currentIndex--;
      System.out.println("Current Index: " + currentIndex);
      reHighlight();
      unHighlightUp();

    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

      System.out.println("Down key pressed.");
      System.out.println("Current Index: " + currentIndex);
      if (currentTab == 0 && partyMember1SkillsLabels.size() - 1 <= currentIndex) {

        System.out.println("Out of bounds for current LinkedList, returning.");
        return;

      } else if (currentTab == 1 && partyMember2SkillsLabels.size() - 1 <= currentIndex) {

        System.out.println("Out of bounds for current LinkedList, returning.");
        return;

      } else if (currentTab == 2 && partyMember3SkillsLabels.size() - 1 <= currentIndex) {

        System.out.println("Out of bounds for current LinkedList, returning.");
        return;

      } else if (currentTab == 3 && partyMember4SkillsLabels.size() - 1 <= currentIndex) {

        System.out.println("Out of bounds for current LinkedList, returning.");
        return;

      } else if (currentTab == 4 && potionsLabels.size() - 1 <= currentIndex) {

        System.out.println("Out of bounds for current LinkedList, returning.");
        return;

      }

      currentIndex++;
      System.out.println("Current Index: " + currentIndex);
      reHighlight();
      unHighlightDown();
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      if (selectedEnemy == 3) {
        System.out.println("Already rightmost enemy, returning");
        return;
      }
      selectedEnemy++;
      System.out.println("Right arrow key pressed: selectedEnemy: " + selectedEnemy);
      enemyPartyLayout[0][selectedEnemy - 1].setBorder(blankEntityBorder);
      enemyPartyLayout[0][selectedEnemy].setBorder(highlightedSkillsBorder);
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      if (selectedEnemy == 0) {
        System.out.println("Already leftmost enemy, returning");
        return;
      }
      selectedEnemy--;
      System.out.println("Left arrow key pressed: selectedEnemy: " + selectedEnemy);
      enemyPartyLayout[0][selectedEnemy + 1].setBorder(blankEntityBorder);
      enemyPartyLayout[0][selectedEnemy].setBorder(highlightedSkillsBorder);
    } else if (e.getKeyCode() == KeyEvent.VK_0) {
      currentPartyMember = 0;
      reHighlight();
    } else if (e.getKeyCode() == KeyEvent.VK_1) {
      currentPartyMember = 1;
      reHighlight();
    } else if (e.getKeyCode() == KeyEvent.VK_2) {
      currentPartyMember = 2;
      reHighlight();
    } else if (e.getKeyCode() == KeyEvent.VK_3) {
      currentPartyMember = 3;
      reHighlight();
    }

  }

  public void keyTyped(KeyEvent e) {

  }

  public void keyReleased(KeyEvent e) {

  }

  public void actionPerformed(ActionEvent e) {

    String action = e.getActionCommand();
    if (action.equals("Skills")) {
      currentIndex = 0;
      currentTab = currentPartyMember;
      System.out.println("Skills pressed, currentTab: " + currentTab);
      unHighlightAll();
      reHighlight();
      skillItemHostSwitchTo();
    } else if (action.equals("Potions")) {
      currentIndex = 0;
      currentTab = 4;
      System.out.println("Potions pressed, currentTab: " + currentTab);
      unHighlightAll();
      reHighlight();
      skillItemHostSwitchTo();
    }

  }

  public void skillsSwitchTo() {

    CardLayout layout = (CardLayout) skills.getLayout();
    if (currentPartyMember == 0) {
      layout.show(skills, "partyMember1");
    } else if (currentPartyMember == 1) {
      layout.show(skills, "partyMember2");
    } else if (currentPartyMember == 2) {
      layout.show(skills, "partyMember3");
    } else if (currentPartyMember == 3) {
      layout.show(skills, "partyMember4");
    }

  }

  public void skillItemHostSwitchTo() {

    CardLayout layout = (CardLayout) skillItemHost.getLayout();
    if (currentTab <= 3) {
      skillsSwitchTo();
      reHighlight();
      layout.show(skillItemHost, "skills");
    } else {
      reHighlight();
      layout.show(skillItemHost, "potions");
    }
  }

  public void reHighlight() {

    if (currentTab == 0) {
      partyMember1SkillsLabels.get(currentIndex).setBorder(highlightedSkillsBorder);
      partyMember1SkillsLabels.get(currentIndex).setForeground(Color.RED);
      partyMember1Skills.revalidate();
    } else if (currentTab == 1) {
      partyMember2SkillsLabels.get(currentIndex).setBorder(highlightedSkillsBorder);
      partyMember2SkillsLabels.get(currentIndex).setForeground(Color.RED);
      partyMember2Skills.revalidate();
    } else if (currentTab == 2) {
      partyMember3SkillsLabels.get(currentIndex).setBorder(highlightedSkillsBorder);
      partyMember3SkillsLabels.get(currentIndex).setForeground(Color.RED);
      partyMember3Skills.revalidate();
    } else if (currentTab == 3) {
      partyMember4SkillsLabels.get(currentIndex).setBorder(highlightedSkillsBorder);
      partyMember4SkillsLabels.get(currentIndex).setForeground(Color.RED);
      partyMember4Skills.revalidate();
    } else if (currentTab == 4) {
      potionsLabels.get(currentIndex).setBorder(highlightedSkillsBorder);
      potionsLabels.get(currentIndex).setForeground(Color.RED);
      potions.revalidate();
    }
    enemyPartyLayout[0][selectedEnemy].setBorder(highlightedSkillsBorder);

  }

  public void unHighlight() {

    if (currentTab == 0) {
      partyMember1SkillsLabels.get(currentIndex).setBorder(defaultSkillsBorder);
      partyMember1SkillsLabels.get(currentIndex).setForeground(Color.BLACK);
      partyMember1Skills.revalidate();
    } else if (currentTab == 1) {
      partyMember2SkillsLabels.get(currentIndex).setBorder(defaultSkillsBorder);
      partyMember2SkillsLabels.get(currentIndex).setForeground(Color.BLACK);
      partyMember2Skills.revalidate();
    } else if (currentTab == 2) {
      partyMember3SkillsLabels.get(currentIndex).setBorder(defaultSkillsBorder);
      partyMember3SkillsLabels.get(currentIndex).setForeground(Color.BLACK);
      partyMember3Skills.revalidate();
    } else if (currentTab == 3) {
      partyMember4SkillsLabels.get(currentIndex).setBorder(defaultSkillsBorder);
      partyMember4SkillsLabels.get(currentIndex).setForeground(Color.BLACK);
      partyMember4Skills.revalidate();
    } else if (currentTab == 4) {
      potionsLabels.get(currentIndex).setBorder(defaultSkillsBorder);
      potionsLabels.get(currentIndex).setForeground(Color.BLACK);
      potions.revalidate();
    }

  }

  public void unHighlightUp() {

    if (currentTab == 0) {
      partyMember1SkillsLabels.get(currentIndex + 1).setBorder(defaultSkillsBorder);
      partyMember1SkillsLabels.get(currentIndex + 1).setForeground(Color.BLACK);
      partyMember1Skills.revalidate();
    } else if (currentTab == 1) {
      partyMember2SkillsLabels.get(currentIndex + 1).setBorder(defaultSkillsBorder);
      partyMember2SkillsLabels.get(currentIndex + 1).setForeground(Color.BLACK);
      partyMember2Skills.revalidate();
    } else if (currentTab == 2) {
      partyMember3SkillsLabels.get(currentIndex + 1).setBorder(defaultSkillsBorder);
      partyMember3SkillsLabels.get(currentIndex + 1).setForeground(Color.BLACK);
      partyMember3Skills.revalidate();
    } else if (currentTab == 3) {
      partyMember4SkillsLabels.get(currentIndex + 1).setBorder(defaultSkillsBorder);
      partyMember4SkillsLabels.get(currentIndex + 1).setForeground(Color.BLACK);
      partyMember4Skills.revalidate();
    } else if (currentTab == 4) {
      potionsLabels.get(currentIndex + 1).setBorder(defaultSkillsBorder);
      potionsLabels.get(currentIndex + 1).setForeground(Color.BLACK);
      potions.revalidate();
    }

  }

  public void unHighlightDown() {

    if (currentTab == 0) {
      partyMember1SkillsLabels.get(currentIndex - 1).setBorder(defaultSkillsBorder);
      partyMember1SkillsLabels.get(currentIndex - 1).setForeground(Color.BLACK);
      partyMember1Skills.revalidate();
    } else if (currentTab == 1) {
      partyMember2SkillsLabels.get(currentIndex - 1).setBorder(defaultSkillsBorder);
      partyMember2SkillsLabels.get(currentIndex - 1).setForeground(Color.BLACK);
      partyMember2Skills.revalidate();
    } else if (currentTab == 2) {
      partyMember3SkillsLabels.get(currentIndex - 1).setBorder(defaultSkillsBorder);
      partyMember3SkillsLabels.get(currentIndex - 1).setForeground(Color.BLACK);
      partyMember3Skills.revalidate();
    } else if (currentTab == 3) {
      partyMember4SkillsLabels.get(currentIndex - 1).setBorder(defaultSkillsBorder);
      partyMember4SkillsLabels.get(currentIndex - 1).setForeground(Color.BLACK);
      partyMember4Skills.revalidate();
    } else if (currentTab == 4) {
      potionsLabels.get(currentIndex - 1).setBorder(defaultSkillsBorder);
      potionsLabels.get(currentIndex - 1).setForeground(Color.BLACK);
      potions.revalidate();
    }

  }

  public void unHighlightAll() {

    for (int i = 0; i < partyMember1SkillsLabels.size(); i++) {
      partyMember1SkillsLabels.get(i).setBorder(defaultSkillsBorder);
      partyMember1SkillsLabels.get(i).setForeground(Color.BLACK);
      partyMember1Skills.revalidate();
    }
    for (int i = 0; i < partyMember2SkillsLabels.size(); i++) {
      partyMember2SkillsLabels.get(i).setBorder(defaultSkillsBorder);
      partyMember2SkillsLabels.get(i).setForeground(Color.BLACK);
      partyMember2Skills.revalidate();
    }
    for (int i = 0; i < partyMember3SkillsLabels.size(); i++) {
      partyMember3SkillsLabels.get(i).setBorder(defaultSkillsBorder);
      partyMember3SkillsLabels.get(i).setForeground(Color.BLACK);
      partyMember3Skills.revalidate();
    }
    for (int i = 0; i < partyMember4SkillsLabels.size(); i++) {
      partyMember4SkillsLabels.get(i).setBorder(defaultSkillsBorder);
      partyMember4SkillsLabels.get(i).setForeground(Color.BLACK);
      partyMember4Skills.revalidate();
    }
    for (int i = 0; i < potionsLabels.size(); i++) {
      potionsLabels.get(i).setBorder(defaultSkillsBorder);
      potionsLabels.get(i).setForeground(Color.BLACK);
      potions.revalidate();
    }
    for (int i = 0; i < 4; i++) {
      enemyPartyLayout[0][i].setBorder(blankEntityBorder);
    }

  }

  public void createPotionsLabels() {
    potionsLabels = ScreenManager.menu.getPotionsLabels();
    ScreenManager.menu.potions.revalidate();
  }

}
