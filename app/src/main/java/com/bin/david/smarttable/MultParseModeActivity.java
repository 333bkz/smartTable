package com.bin.david.smarttable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.draw.TextImageDrawFormat;
import com.bin.david.form.data.format.title.TitleImageDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.utils.DensityUtils;
import com.bin.david.smarttable.bean.DevicePosition;
import com.bin.david.smarttable.bean.TableStyle;
import com.bin.david.smarttable.view.BaseCheckDialog;
import com.bin.david.smarttable.view.BaseDialog;
import com.bin.david.smarttable.view.QuickChartDialog;
import com.daivd.chart.component.axis.BaseAxis;
import com.daivd.chart.component.base.IAxis;
import com.daivd.chart.component.base.IComponent;
import com.daivd.chart.core.LineChart;
import com.daivd.chart.data.ChartData;
import com.daivd.chart.data.LineData;
import com.daivd.chart.data.style.PointStyle;
import com.daivd.chart.provider.component.cross.VerticalCross;
import com.daivd.chart.provider.component.level.LevelLine;
import com.daivd.chart.provider.component.mark.BubbleMarkView;
import com.daivd.chart.provider.component.point.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultParseModeActivity extends AppCompatActivity implements View.OnClickListener {

    private SmartTable<DevicePosition> table;
    private BaseCheckDialog<TableStyle> chartDialog;
    private QuickChartDialog quickChartDialog;
    private Map<String, Bitmap> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        quickChartDialog = new QuickChartDialog();
        //FontStyle.setDefaultTextSize(DensityUtils.sp2px(this, 15)); //设置全局字体大小
        table = (SmartTable<DevicePosition>) findViewById(R.id.table);
        final List<DevicePosition> testData = new ArrayList<>();
        DevicePosition item1 = new DevicePosition("消防供配电设施", "消防电源", "电源箱外观", "正常", "");
        DevicePosition item2 = new DevicePosition("消防供配电设施", "消防电源", "主电源工作状态", "正常", "");
        DevicePosition item3 = new DevicePosition("消防供配电设施", "消防电源", "备用电源工作状态", "正常", "");
        DevicePosition item4 = new DevicePosition("消防供配电设施", "发电机", "发电机启动装置外观及工作状态", "正常", "");
        DevicePosition item5 = new DevicePosition("消防供配电设施", "发电机", "发电机燃料储量、储油间环境", "正常", "");
        DevicePosition item6 = new DevicePosition("火灾自动报警系统", "火灾报警控制器", "火灾报警控制器运行状况", "正常", "");
        DevicePosition item7 = new DevicePosition("火灾自动报警系统", "火灾报警控制器", "火灾显示器运行状况", "正常", "");
        DevicePosition item8 = new DevicePosition("火灾自动报警系统", "火灾报警控制器", "CRT图形显示器运行状况", "正常", "");
        DevicePosition item9 = new DevicePosition("火灾自动报警系统", "火灾报警装置", "火灾报警装置外观", "正常", "");
        DevicePosition item10 = new DevicePosition("火灾自动报警系统", "火灾报警装置", "远程监控装置外观", "正常", "");
        testData.add(item1);
        testData.add(item2);
        testData.add(item3);
        testData.add(item4);
        testData.add(item5);
        testData.add(item6);
        testData.add(item7);
        testData.add(item8);
        testData.add(item9);
        testData.add(item10);

        final Column<String> system = new Column<>("巡查项目", "system");
        final Column<String> device = new Column<>("设备", "device");
        final Column<String> position = new Column<>("设备位置", "position");
        final Column<String> state = new Column<>("状态", "state");
        final Column<String> remark = new Column<>("备注", "remark");
        system.setAutoMerge(true);
        device.setAutoMerge(true);
        remark.setWidth(200);
        int size = DensityUtils.dp2px(this, 15);
        Column totalColumn1 = new Column("巡查内容(设备数)", device, position);
        Column totalColumn2 = new Column("巡查情况", state, remark);
        final TableData<DevicePosition> tableData = new TableData<>("建筑消防设施巡查记录汇总表", testData, system,
                totalColumn1, totalColumn2);
        table.getConfig().setColumnTitleBackground(new BaseBackgroundFormat(getResources().getColor(R.color.windows_bg)));
        table.getConfig().setCountBackground(new BaseBackgroundFormat(getResources().getColor(R.color.windows_bg)));
        tableData.setTitleDrawFormat(new TitleImageDrawFormat(size, size, TitleImageDrawFormat.RIGHT, 10) {
            @Override
            protected Context getContext() {
                return MultParseModeActivity.this;
            }

            @Override
            protected int getResourceID(Column column) {
                if (!column.isParent()) {
                    if (tableData.getSortColumn() == column) {
                        setDirection(TextImageDrawFormat.RIGHT);
                        if (column.isReverseSort()) {
                            return R.mipmap.sort_up;
                        }
                        return R.mipmap.sort_down;

                    } else {
                        setDirection(TextImageDrawFormat.LEFT);
                        if (column == state) {
                            return R.mipmap.name;
                        } else if (column == remark) {
                            return R.mipmap.age;
                        }
                    }
                    return 0;
                }
                setDirection(TextImageDrawFormat.LEFT);
                int level = tableData.getTableInfo().getMaxLevel() - column.getLevel();
                if (level == 0) {
                    return R.mipmap.level1;
                } else if (level == 1) {
                    return R.mipmap.level2;
                }
                return 0;
            }
        });
        state.setOnColumnItemClickListener((column, value, s, index)
                -> {
            Log.e("-----", value + " - " + s + " - " + index);
            //testData.get(index).setState(value + "-");
            //column.getDatas().get(index).
            //table.notifyDataChanged();
        });
        //table.setZoom(true,1.5f,0.2f);
        //tableData.setShowCount(true);
        table.getConfig().setShowXSequence(false);
        table.getConfig().setShowYSequence(false);
        table.getConfig().setShowTableTitle(false);
        table.setTableData(tableData);
    }

    @Override
    public void onClick(View view) {
        changedStyle();
    }

    private void changedStyle() {

        if (chartDialog == null) {
            chartDialog = new BaseCheckDialog<>("表格设置", new BaseCheckDialog.OnCheckChangeListener<TableStyle>() {
                @Override
                public String getItemText(TableStyle chartStyle) {
                    return chartStyle.value;
                }

                @Override
                public void onItemClick(TableStyle item, int position) {
                    switch (item) {
                        case FIXED_TITLE:
                            fixedTitle(item);
                            break;
                        case FIXED_X_AXIS:
                            fixedXAxis(item);
                            break;
                        case FIXED_Y_AXIS:
                            fixedYAxis(item);
                            break;
                        case FIXED_FIRST_COLUMN:
                            fixedFirstColumn(item);
                            break;
                        case FIXED_COUNT_ROW:
                            fixedCountRow(item);
                            break;
                        case ZOOM:
                            zoom(item);
                            break;

                    }
                }
            });
        }
        ArrayList<TableStyle> items = new ArrayList<>();

        items.add(TableStyle.FIXED_X_AXIS);
        items.add(TableStyle.FIXED_Y_AXIS);
        items.add(TableStyle.FIXED_TITLE);
        items.add(TableStyle.FIXED_FIRST_COLUMN);
        items.add(TableStyle.FIXED_COUNT_ROW);
        items.add(TableStyle.ZOOM);
        chartDialog.show(this, true, items);
    }

    private void zoom(TableStyle item) {
        quickChartDialog.showDialog(this, item, new String[]{"缩放", "不缩放"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.setZoom(true, 3, 1);
                } else if (position == 1) {
                    table.setZoom(false, 3, 1);
                }
                table.invalidate();
            }
        });
    }

    private void fixedXAxis(TableStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getConfig().setFixedXSequence(true);
                } else if (position == 1) {
                    table.getConfig().setFixedXSequence(false);
                }
                table.invalidate();
            }
        });
    }

    private void fixedYAxis(TableStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getConfig().setFixedYSequence(true);
                } else if (position == 1) {
                    table.getConfig().setFixedYSequence(false);
                }
                table.invalidate();
            }
        });
    }

    private void fixedTitle(TableStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getConfig().setFixedTitle(true);
                } else if (position == 1) {
                    table.getConfig().setFixedTitle(false);
                }
                table.invalidate();
            }
        });
    }

    private void fixedFirstColumn(TableStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getConfig().setFixedFirstColumn(true);
                } else if (position == 1) {
                    table.getConfig().setFixedFirstColumn(false);
                }
                table.invalidate();
            }
        });
    }

    private void fixedCountRow(TableStyle c) {

        quickChartDialog.showDialog(this, c, new String[]{"固定", "不固定"}, new QuickChartDialog.OnCheckChangeAdapter() {

            @Override
            public void onItemClick(String s, int position) {
                if (position == 0) {
                    table.getConfig().setFixedCountRow(true);
                } else if (position == 1) {
                    table.getConfig().setFixedCountRow(false);
                }
                table.invalidate();
            }
        });
    }

    /**
     * 测试是否可以兼容之前smartChart
     *
     * @param tableName
     * @param chartYDataList
     * @param list
     */
    private void showChartDialog(String tableName, List<String> chartYDataList, List<Integer> list) {
        View chartView = View.inflate(this, R.layout.dialog_chart, null);
        LineChart lineChart = (LineChart) chartView.findViewById(R.id.lineChart);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        Resources res = getResources();
        com.daivd.chart.data.style.FontStyle.setDefaultTextSpSize(this, 12);
        List<LineData> ColumnDatas = new ArrayList<>();
        ArrayList<Double> tempList1 = new ArrayList<>();
        ArrayList<String> ydataList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String value = chartYDataList.get(i);
            ydataList.add(value);
        }
        for (int i = 0; i < 30; i++) {
            int value = list.get(i);
            tempList1.add(Double.valueOf(value));
        }
        LineData columnData1 = new LineData(tableName, "", IAxis.AxisDirection.LEFT, getResources().getColor(R.color.arc1), tempList1);
        ColumnDatas.add(columnData1);
        ChartData<LineData> chartData2 = new ChartData<>("Area Chart", ydataList, ColumnDatas);
        lineChart.getChartTitle().setDirection(IComponent.TOP);
        lineChart.getLegend().setDirection(IComponent.BOTTOM);
        lineChart.setLineModel(LineChart.CURVE_MODEL);
        BaseAxis verticalAxis = lineChart.getLeftVerticalAxis();
        BaseAxis horizontalAxis = lineChart.getHorizontalAxis();
        //设置竖轴方向
        verticalAxis.setAxisDirection(IAxis.AxisDirection.LEFT);
        //设置网格
        verticalAxis.setDrawGrid(true);
        //设置横轴方向
        horizontalAxis.setAxisDirection(IAxis.AxisDirection.BOTTOM);
        horizontalAxis.setDrawGrid(true);
        //设置线条样式
        verticalAxis.getAxisStyle().setWidth(this, 1);
        DashPathEffect effects = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);
        verticalAxis.getGridStyle().setWidth(this, 1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        horizontalAxis.getGridStyle().setWidth(this, 1).setColor(res.getColor(R.color.arc_text)).setEffect(effects);
        lineChart.setZoom(true);
        //开启十字架
        lineChart.getProvider().setOpenCross(true);
        lineChart.getProvider().setCross(new VerticalCross());
        lineChart.getProvider().setShowText(true);
        //开启MarkView
        lineChart.getProvider().setOpenMark(true);
        //设置MarkView
        lineChart.getProvider().setMarkView(new BubbleMarkView(this));

        //设置显示标题
        lineChart.setShowChartName(true);
        //设置标题样式
        com.daivd.chart.data.style.FontStyle fontStyle = lineChart.getChartTitle().getFontStyle();
        fontStyle.setTextColor(res.getColor(R.color.arc_temp));
        fontStyle.setTextSpSize(this, 15);
        LevelLine levelLine = new LevelLine(30);
        DashPathEffect effects2 = new DashPathEffect(new float[]{1, 2, 2, 4}, 1);
        levelLine.getLineStyle().setWidth(this, 1).setColor(res.getColor(R.color.arc23)).setEffect(effects);
        levelLine.getLineStyle().setEffect(effects2);
        lineChart.getProvider().addLevelLine(levelLine);
        Point legendPoint = (Point) lineChart.getLegend().getPoint();
        PointStyle style = legendPoint.getPointStyle();
        style.setShape(PointStyle.SQUARE);
        lineChart.getProvider().setArea(true);
        lineChart.getHorizontalAxis().setRotateAngle(90);
        lineChart.setChartData(chartData2);
        lineChart.startChartAnim(400);
        BaseDialog dialog = new BaseDialog.Builder(this).setFillWidth(true).setContentView(chartView).create();
        dialog.show();
    }

}
