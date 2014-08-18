package chart;

import org.jfree.chart.*;
import org.jfree.data.category.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.*;
import org.jfree.data.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.plot.*;
import java.awt.*;

public class BarChartBean {
	
	//java 차트 생성
	public JFreeChart getBarChart(int sur_seq, String cnt, String[] title, String[] i_title1, String[] i_title2, String[] i_title3, String[] i_title4, String[] i_title5){
								//	sur_seq값, 문항개수, 
		
		System.out.println(title.length);
		System.out.println(i_title1.length);
		System.out.println(i_title2.length);
		System.out.println(i_title3.length);
		System.out.println(i_title4.length);
		System.out.println(i_title5.length);
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(1, "count", "Raaa");
		dataset.setValue(2, "count", "Vinod");
		dataset.setValue(3, "count", "Deepak");
		dataset.setValue(4, "count", "Prashant");
		dataset.setValue(5, "count", "55555");
		dataset.setValue(6, "count", "6666666");
		dataset.setValue(7, "count", "777777");
		dataset.setValue(8, "count", "888888");
		dataset.setValue(9, "Marks", "999999");
		
		JFreeChart chart = ChartFactory.createBarChart("Selected Result", "item", "count", dataset, PlotOrientation.VERTICAL, false, true, false);
		System.out.println(chart.getTitle().getFont().getName()); // 폰트 : Tahoma
		
		chart.setBackgroundPaint(Color.white);
		chart.getTitle().setPaint(Color.blue); 
		CategoryPlot p = chart.getCategoryPlot(); 
		p.setRangeGridlinePaint(Color.red); 
		return chart;
	}
}