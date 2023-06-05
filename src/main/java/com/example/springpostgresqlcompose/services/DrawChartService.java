package com.example.springpostgresqlcompose.services;

import com.example.springpostgresqlcompose.constants.AppConstants;
import com.example.springpostgresqlcompose.db.repositories.StudentRepository;
import com.example.springpostgresqlcompose.dtos.MarksDistribution;
import com.example.springpostgresqlcompose.dtos.StudentCountDTO;
import lombok.RequiredArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DrawChartService {

    private final StudentRepository studentRepository;

    public String createPieChart(DefaultPieDataset dataset, String filePath, String classId) throws IOException {

        JFreeChart jFreeChart = ChartFactory.createPieChart(
                "Obtained Marks Distribution (Class : " + classId + ")",  //pie chart title
                dataset, //pie chart dataset
                true, true, false);  //pie chart> legend, tooltips and urls

        PiePlot plot = (PiePlot) jFreeChart.getPlot();
        plot.setSimpleLabels(false);
        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0} : {1}");
        plot.setLabelGenerator(gen);

        ChartUtilities.saveChartAsPNG(new File(filePath), jFreeChart, 1080, 1080);

        return "Created";
    }

    public String createBarChart(CategoryDataset defaultCategoryDataset, String filePath, String classId) throws IOException {

        JFreeChart jFreeChart = ChartFactory.createBarChart(
                "Summary (Class : " + classId + ")", //title
                "School-wise count", // categoryAxisLabel
                "Student Count", //valueAxisLabel
                defaultCategoryDataset, //dataset
                PlotOrientation.VERTICAL, //orientation
                true, true, false); //legend, tooltips and urls

        CategoryPlot plot = (CategoryPlot) jFreeChart.getPlot();

        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
                TextAnchor.TOP_CENTER);
        renderer.setBasePositiveItemLabelPosition(position);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);

        ChartUtilities.saveChartAsPNG(new File(filePath), jFreeChart, 1920, 1080);

        return "Created";
    }

    public String createStudentBarChart() throws IOException {
        final String TEN = "Ten";
        final String EIGHT = "Eight";
        final String FIVE = "Five";

        List<StudentCountDTO> tenRegistered = studentRepository.getRegisteredStudent(TEN);
        List<StudentCountDTO> tenParticipated = studentRepository.getParticipatedStudent(TEN);
        List<StudentCountDTO> tenObtained20PercentMarks = studentRepository.getStudentObtainedMarksAbove(TEN, 40.0);
        List<StudentCountDTO> tenInsideMeritPosition = studentRepository.getStudentInsideMeritList(TEN, 25);

        CategoryDataset tenCategoryDataset = createStudentDataset(tenRegistered, tenParticipated, tenObtained20PercentMarks, tenInsideMeritPosition);
        createBarChart(tenCategoryDataset, AppConstants.INPUT_OUTPUT_FILE_DIRECTORY + "ten-bar-chart.png", "9-10");

        List<StudentCountDTO> eightRegistered = studentRepository.getRegisteredStudent(EIGHT);
        List<StudentCountDTO> eightParticipated = studentRepository.getParticipatedStudent(EIGHT);
        List<StudentCountDTO> eightObtained20PercentMarks = studentRepository.getStudentObtainedMarksAbove(EIGHT, 40.0);
        List<StudentCountDTO> eightInsideMeritPosition = studentRepository.getStudentInsideMeritList(EIGHT, 25);

        CategoryDataset eightCategoryDataset = createStudentDataset(eightRegistered, eightParticipated, eightObtained20PercentMarks, eightInsideMeritPosition);
        createBarChart(eightCategoryDataset, AppConstants.INPUT_OUTPUT_FILE_DIRECTORY + "eight-bar-chart.png", "08");

        List<StudentCountDTO> fiveRegistered = studentRepository.getRegisteredStudent(FIVE);
        List<StudentCountDTO> fiveParticipated = studentRepository.getParticipatedStudent(FIVE);
        List<StudentCountDTO> fiveObtained20PercentMarks = studentRepository.getStudentObtainedMarksAbove(FIVE, 40.0);
        List<StudentCountDTO> fiveInsideMeritPosition = studentRepository.getStudentInsideMeritList(FIVE, 25);

        CategoryDataset fiveCategoryDataset = createStudentDataset(fiveRegistered, fiveParticipated, fiveObtained20PercentMarks, fiveInsideMeritPosition);
        createBarChart(fiveCategoryDataset, AppConstants.INPUT_OUTPUT_FILE_DIRECTORY + "five-bar-chart.png", "05");

        List<MarksDistribution> tenMarksDistribution = studentRepository.getMarksDistribution(TEN);
        List<MarksDistribution> eightMarksDistribution = studentRepository.getMarksDistribution(EIGHT);
        List<MarksDistribution> fiveMarksDistribution = studentRepository.getMarksDistribution(FIVE);

        DefaultPieDataset tenDataset = createMarksDataset(tenMarksDistribution);
        DefaultPieDataset eightDataset = createMarksDataset(eightMarksDistribution);
        DefaultPieDataset fiveDataset = createMarksDataset(fiveMarksDistribution);

        createPieChart(tenDataset, AppConstants.INPUT_OUTPUT_FILE_DIRECTORY + "ten-pie-chart.png", "9-10");
        createPieChart(eightDataset, AppConstants.INPUT_OUTPUT_FILE_DIRECTORY + "eight-pie-chart.png", "08");
        createPieChart(fiveDataset, AppConstants.INPUT_OUTPUT_FILE_DIRECTORY + "five-pie-chart.png", "05");

        return "Successfully Created!";
    }

    private CategoryDataset createStudentDataset(List<StudentCountDTO> registered, List<StudentCountDTO> participated
            , List<StudentCountDTO> twentyPercentMarks, List<StudentCountDTO> insideMeritList) {

        final DefaultCategoryDataset dataset =
                new DefaultCategoryDataset();

        for (StudentCountDTO studentCountDTO : registered) {
            dataset.addValue(studentCountDTO.getCount(), "Registered", studentCountDTO.getSchoolName());
        }

        for (StudentCountDTO studentCountDTO : participated) {
            dataset.addValue(studentCountDTO.getCount(), "Participated in Exam", studentCountDTO.getSchoolName());
        }

        for (StudentCountDTO studentCountDTO : twentyPercentMarks) {
            dataset.addValue(studentCountDTO.getCount(), "Obtained 20+% Marks", studentCountDTO.getSchoolName());
        }

        for (StudentCountDTO studentCountDTO : insideMeritList) {
            dataset.addValue(studentCountDTO.getCount(), "Placed in Top 25", studentCountDTO.getSchoolName());
        }

        return dataset;
    }

    private DefaultPieDataset createMarksDataset(List<MarksDistribution> marksDistributions) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (MarksDistribution marksDistribution : marksDistributions) {
            dataset.setValue("(" + marksDistribution.getMarkRange() + ")%", marksDistribution.getCount());
        }

        return dataset;
    }
}
