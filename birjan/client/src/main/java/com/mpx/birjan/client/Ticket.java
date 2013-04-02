package com.mpx.birjan.client;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.List;

public class Ticket implements Printable {

	String lottery;
	String variant;
	List<List<Object>> dataVector;
	String hash;

	public Ticket(String lottery, String variant,
			List<List<Object>> dataVector, String hash) {
		this.lottery = lottery;
		this.variant = variant;
		this.dataVector = dataVector;
		this.hash = hash;
	}

	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex) {
		if (pageIndex >= 1) {
			return Printable.NO_SUCH_PAGE;
		}

		Graphics2D g2d = (Graphics2D) g;
		// Be careful of clips...
		g2d.translate((int) pf.getImageableX(), (int) pf.getImageableY());

		double width = pf.getImageableWidth();
		double height = pf.getImageableHeight();

		int fontSize = 8;

		Font fnt = new Font("Helvetica", Font.PLAIN, fontSize);
		Font fnt2 = new Font("Helvetica", Font.PLAIN, fontSize * 2);
		g.setFont(fnt);
		// g2d.drawRect(0, 0, (int)pageFormat.getImageableWidth() - 1,
		// (int)pageFormat.getImageableHeight() - 1);
		FontMetrics fm = g2d.getFontMetrics();
		String text = "29/03/2013";
		g2d.drawString(text, fontSize / 2, fontSize);
		text = "17:12";
		g2d.drawString(text, (int) (width - fm.stringWidth(text)), fontSize);

		g.setFont(fnt2);
		fm = g2d.getFontMetrics();
		text = "XXQWERTY12";
		g2d.drawString(text, (int) width / 2 - fm.stringWidth(text) / 2,
				fontSize * 4);

		g.setFont(fnt);
		fm = g2d.getFontMetrics();
		text = "NAM - 29/03/2012";
		g2d.drawString(text, fontSize / 2, fontSize * 6);

		text = "1.00   xx02   01";
		int x = (int) (fontSize * 4 - fm.stringWidth(text.split("\\.")[0]));

		g2d.drawString(text, x, fontSize * 8);

		text = "11.00   xx02   01";
		x = (int) (fontSize * 4 - fm.stringWidth(text.split("\\.")[0]));
		g2d.drawString(text, x, fontSize * 10);

		text = "** ********** **";
		g2d.drawString(text, (int) width / 2 - fm.stringWidth(text) / 2,
				fontSize * 12);

		// g.setFont(fnt);
		// text = "bottom";
		// double x = width - fm.stringWidth(text);
		// double y = (height - fm.getHeight()) + fm.getAscent();
		// g2d.drawString(text, (int)x, (int)y);

		return Printable.PAGE_EXISTS;
	}

	public String getLottery() {
		return lottery;
	}

	public String getVariant() {
		return variant;
	}

	public List<List<Object>> getDataVector() {
		return dataVector;
	}

	public String getHash() {
		return hash;
	}

}
