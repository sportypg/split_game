import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.Timer;

public class SplitPanel extends JPanel {

	final Vector dimensions;
	SplitGameMap gm;
	private Timer t;
	Image horizontal, vertical, img, gameOverImage, playAgainButton;
	private int level;
	private int currentAreaAvailable;
	private JProgressBar progressBar;
	private int areaCutOff;
	private String typeOfDivider = null;
	JTextField levelsField;
	JTextField l;
	private final int startingAreaAvailable;
	private JPanel buttonPanel;
	private JPanel iAmDead;
	private JButton horizontalDividerButton;
	private JButton verticalDividerButton;

	public SplitPanel(int width, int length) {
		this.setLayout(new BorderLayout());
		dimensions = new Vector(width, length);
		openBackgroundImg();
		Color backgroundColor = Color.GREEN;
		this.setBackground(backgroundColor);

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent click) {
				Divider div = null;
				if (gm.getPolygon().inside(click.getX(), click.getY())) {
					if (typeOfDivider != null) {
						if (typeOfDivider.equals("vertical")) {
							div = new VerticalDivider(click.getX(), click.getY(), gm.getPolygon(), gm.getBall(), gm);
						} else {
							div = new HorizontalDivider(click.getX(), click.getY(), gm.getPolygon(), gm.getBall(), gm);
						}
					}
				}
				if (div != null) {
					gm.addDivider(div);
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		level = 1;
		beginGame();
		startingAreaAvailable = gm.getPolygon().getHeight() * gm.getPolygon().getWidth();
		currentAreaAvailable = startingAreaAvailable;
		progressBar = new JProgressBar();
		setUpProgressBar(width, length);
		areaCutOff = 0;
	}

	private void openBackgroundImg() {
		URL backUrl = this.getClass().getResource("background.jpg");
		Toolkit tk = Toolkit.getDefaultToolkit();
		img = tk.getImage(backUrl);
	}

	private void setUpProgressBar(int width, int length) {
		JPanel barPanel = new JPanel();
		barPanel.setLayout(new GridLayout());
		barPanel.setBackground(Color.CYAN);
		barPanel.setPreferredSize(new Dimension(100, 20));
		progressBar.setMinimum(0);
		progressBar.setMaximum(currentAreaAvailable / 2);
		progressBar.setOpaque(true);
		progressBar.setStringPainted(true);
		progressBar.setBackground(Color.blue);
		progressBar.setForeground(Color.GRAY);
		progressBar.setVisible(true);
		progressBar.setPreferredSize(new Dimension(gm.getWidth() / 2, 20));
		barPanel.add(progressBar);
		barPanel.add(createLevels());
		this.add(barPanel, BorderLayout.NORTH);
		updateBar();
	}

	private Component createLevels() {
		levelsField = new JTextField();
		levelsField.setText("Level: " + level);
		levelsField.setPreferredSize(new Dimension(gm.getWidth() / 2, 20));
		return levelsField;
	}

	private void updateLevels() {
		levelsField.setHorizontalAlignment(levelsField.CENTER);
		levelsField.setText("Level: " + level);
	}

	public void updateBar() {
		if (progressBar.getString().equals("100%")) {
			gm.newExpansion(gm.getPolygon().expand());
			currentAreaAvailable = gm.getPolygon().getWidth() * gm.getPolygon().getHeight();
			progressBar.setMaximum((currentAreaAvailable / 100) * (50 + (5 * (level - 1))));
			System.out.println("width: " + gm.getPolygon().getWidth());
			System.out.println("height: " + gm.getPolygon().getHeight());
			System.out.println("currentArea: " + currentAreaAvailable);
			System.out.println("max: " + progressBar.getMaximum());
			areaCutOff = 0;
			/*
			 * try { Thread.sleep(1000); } catch (InterruptedException e) {
			 * e.printStackTrace(); }
			 */
			// i dont think we should pause stuff
			level++;
		}
		if (gm.getPolygon() != null) {
			areaCutOff = currentAreaAvailable - gm.getPolygon().getHeight() * gm.getPolygon().getWidth();
		}
		progressBar.setValue(areaCutOff);
	}

	private void beginGame() {
		this.setPreferredSize(new Dimension(dimensions.getX(), dimensions.getY()));
		createMap();
		createDividerButtons();
		startTicks();
	}

	private void createDividerButtons() {
		openImages();

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		horizontalDividerButton = new JButton();
		verticalDividerButton = new JButton();

		horizontalDividerButton.setIcon(new ImageIcon(horizontal));
		verticalDividerButton.setIcon(new ImageIcon(vertical));

		horizontalDividerButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				typeOfDivider = "horizontal";
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		verticalDividerButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				typeOfDivider = "vertical";
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});

		buttonPanel.add(horizontalDividerButton);
		buttonPanel.add(verticalDividerButton);
		buttonPanel.setBackground(Color.WHITE);

		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	private void openImages() {
		URL horizontalUrl = this.getClass().getResource("hoirzontal.png");
		Toolkit tk = Toolkit.getDefaultToolkit();
		horizontal = tk.getImage(horizontalUrl);

		URL verticalUrl = this.getClass().getResource("vertical.png");
		vertical = tk.getImage(verticalUrl);

		URL gameOverUrl = this.getClass().getResource("THEBACKGROUND.jpg");
		gameOverImage = tk.getImage(gameOverUrl);

		URL playAgainUrl = this.getClass().getResource("playAgain.png");
		playAgainButton = tk.getImage(playAgainUrl);
	}

	private void startTicks() {
		t = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gm.tick();
				repaint();
				Toolkit.getDefaultToolkit().sync();
			}
		});
		t.start();
	}

	private void createMap() {
		gm = new SplitGameMap(dimensions);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!gm.getGameOver()) {
			updateLevels();
			updateBar();
			g.drawImage(img, 0, 0, dimensions.getX(), dimensions.getY(), null);
			gm.draw(g);
		} else {
			buttonPanel.remove(horizontalDividerButton);
			buttonPanel.remove(verticalDividerButton);
			// this.removeAll();
			g.drawImage(gameOverImage, 0, 0, dimensions.getX(), dimensions.getY(), null);
			iAmDead = new JPanel();
			iAmDead.setLayout(new FlowLayout());

			JButton playAgain = new JButton();
			playAgain.setIcon(new ImageIcon(playAgainButton));

			playAgain.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
				}
			});
			buttonPanel.setBackground(Color.WHITE);
			iAmDead.add(playAgain);
			this.add(playAgain, BorderLayout.SOUTH);
			// g.drawImage(gameOverImage, 0, 0, null);
		}
	}
}
