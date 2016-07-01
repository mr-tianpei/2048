package com.mr.game2048;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {

	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}

	private void initGameView() {
		setColumnCount(4);
		setBackgroundColor(0xffbbada0);
		setOnTouchListener(new View.OnTouchListener() {
			private float startX, startY, offsetX, offsetY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;
					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX > 5) {
							swipeRight();
						} else if (offsetX < -5) {
							swipeLeft();
						}
					} else {
						if (offsetY > 5) {
							swipeDown();
						} else if (offsetY < -5) {
							swipeUp();
						}
					}
					break;
				}
				return true;
			}
		});
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int cardWidth = (Math.min(w, h) - 10) / 4;
		addCard(cardWidth, cardWidth);
		startGame();
	}

	private void addCard(int CardWidth, int CardHeight) {
		Card c;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				c = new Card(getContext());
				c.setNum(2);
				addView(c, CardWidth, CardHeight);
				cardMap[x][y] = c;
			}
		}
	}

	private void addRandomCard() {
		emptyPoint.clear();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardMap[x][y].getNum() <= 0) {
					emptyPoint.add(new Point(x, y));
				}
			}
		}
		Point p = emptyPoint.remove((int) (Math.random() * emptyPoint.size()));
		cardMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
	}

	private void startGame() {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardMap[x][y].setNum(0);
			}
		}
		MainActivity.getMainActivity().clearScore();
		MainActivity.getMainActivity().showBest();
		addRandomCard();
		addRandomCard();
	}

	private void swipeLeft() {
		boolean isChange = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				for (int x1 = x + 1; x1 < 4; x1++) {
					if (cardMap[x1][y].getNum() > 0) {
						if (cardMap[x][y].getNum() <= 0) {
							cardMap[x][y].setNum(cardMap[x1][y].getNum());
							cardMap[x1][y].setNum(0);

							x--;// 多遍历一变。为了？
							isChange = true;
						} else if (cardMap[x1][y].equals(cardMap[x][y])) {
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							cardMap[x1][y].setNum(0);
							MainActivity.getMainActivity().addScore(
									cardMap[x][y].getNum());
							isChange = true;
						}
						break;
					}
				}
			}
		}
		if (isChange) {
			succeed();
			addRandomCard();
			checkComplete();
		}
	}

	private void swipeRight() {
		boolean isChange = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {
				for (int x1 = x - 1; x1 >= 0; x1--) {
					if (cardMap[x1][y].getNum() > 0) {
						if (cardMap[x][y].getNum() <= 0) {
							cardMap[x][y].setNum(cardMap[x1][y].getNum());
							cardMap[x1][y].setNum(0);

							x++;// 多遍历一变。为了？
							isChange = true;
						} else if (cardMap[x1][y].equals(cardMap[x][y])) {
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							cardMap[x1][y].setNum(0);
							MainActivity.getMainActivity().addScore(
									cardMap[x][y].getNum());
							isChange = true;
						}
						break;
					}
				}
			}
		}
		if (isChange) {
			succeed();
			addRandomCard();
			checkComplete();
		}
	}

	private void swipeUp() {
		boolean isChange = false;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				for (int y1 = y + 1; y1 < 4; y1++) {
					if (cardMap[x][y1].getNum() > 0) {
						if (cardMap[x][y].getNum() <= 0) {
							cardMap[x][y].setNum(cardMap[x][y1].getNum());
							cardMap[x][y1].setNum(0);

							y--;// 多遍历一变。为了？
							isChange = true;
						} else if (cardMap[x][y1].equals(cardMap[x][y])) {
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							cardMap[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(
									cardMap[x][y].getNum());
							isChange = true;
						}
						break;
					}
				}
			}
		}
		if (isChange) {
			succeed();
			addRandomCard();
			checkComplete();
		}
	}

	private void swipeDown() {
		boolean isChange = false;
		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {
				for (int y1 = y - 1; y1 >= 0; y1--) {
					if (cardMap[x][y1].getNum() > 0) {
						if (cardMap[x][y].getNum() <= 0) {
							cardMap[x][y].setNum(cardMap[x][y1].getNum());
							cardMap[x][y1].setNum(0);

							y++;// 多遍历一变。为了？
							isChange = true;
						} else if (cardMap[x][y1].equals(cardMap[x][y])) {
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							cardMap[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(
									cardMap[x][y].getNum());
							isChange = true;
						}
						break;
					}
				}
			}
		}
		if (isChange) {
			succeed();
			addRandomCard();
			checkComplete();
		}
	}

	private void checkComplete() {
		boolean complete = true;
		All: for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardMap[x][y].getNum() == 0
						|| (x > 0 && cardMap[x][y].equals(cardMap[x - 1][y]))
						|| (x < 3 && cardMap[x][y].equals(cardMap[x + 1][y]))
						|| (y > 0 && cardMap[x][y].equals(cardMap[x][y - 1]))
						|| (y < 3 && cardMap[x][y].equals(cardMap[x][y + 1]))) {
					complete = false;
					break All;
				}
			}
		}
		if (complete) {
			MainActivity.getMainActivity().prefBestScore();
			new AlertDialog.Builder(getContext())
					.setTitle("你好")
					.setMessage("游戏结束")
					.setCancelable(false)
					.setPositiveButton("重来",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									startGame();
								}
							}).show();
		}
	}

	private void succeed() {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardMap[x][y].getNum() == 2048) {
					new AlertDialog.Builder(getContext())
							.setTitle("恭喜恭喜")
							.setMessage("恭喜你！成功了！")
							.setPositiveButton("继续",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											startGame();
										}
									}).show();
				}
			}
		}
	}

	private Card[][] cardMap = new Card[4][4];
	private List<Point> emptyPoint = new ArrayList<Point>();
}