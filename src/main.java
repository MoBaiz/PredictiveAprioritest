import javafx.util.Pair;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by 38147 on 2017/10/31.
 */
public class main {
    public static void main(String args[]) throws Exception {
        DB db=new DB("jdbc:mysql://localhost:3306/eye","root","zch123");
//        String sql="select * from nomogram where eye=0";
//        Instances allitems=db.getData(sql);
//        Instances items=db.getData(sql);
//        //删除无用属性_14
//        for(int i=18;i<42;i++){
//            items.deleteAttributeAt(items.numAttributes()-1);
//        }
//        items.deleteAttributeAt(5);
//        items.deleteAttributeAt(2);
//        items.deleteAttributeAt(1);
//        items.deleteAttributeAt(0);
        //医生选择的
//        Instances allitems=db.getData("select * from knn where eye=0");
//        Instances items=db.getData("select * from knn where eye=0");
        Instances allitems=db.getData("select * from old where eye='OD'");
        Instances items=db.getData("select * from old where eye='OD'");
        items.deleteAttributeAt(items.numAttributes()-1);
        items.deleteAttributeAt(7);
        items.deleteAttributeAt(6);
        items.deleteAttributeAt(5);
        items.deleteAttributeAt(3);
        items.deleteAttributeAt(1);
        items.deleteAttributeAt(0);
        //属性归一化
        Normalize normalize = new Normalize();
        normalize.setInputFormat(items);
        items= Filter.useFilter(items, normalize);

        ArrayList<Pair<Instance,Pair<Instance,Double>>> list=new ArrayList<>();
        //计算距离bing保存到output_string中
        StringBuffer output_string=new StringBuffer();
        String title="";
        for(int i=0;i<allitems.numAttributes();i++) {
           title+=allitems.attribute(i).name()+",";
        }
        title+=",,distance\n";
        for(int j=0;j<items.numInstances();j++){
            output_string.append(title);
            list.clear();
        for(int i=0;i<items.numInstances();i++){//增加原属性和删除后属性的映射
            list.add(new Pair(allitems.instance(i),new Pair(items.instance(i),distance(items.instance(j),items.instance(i)))));
        }
        sort(list);
            output_string.append(list.get(0).getKey()+",,,"+list.get(0).getValue().getValue()+"\n");
            output_string.append("Rank:\n");
        for(int i=1;i<11;i++){
            output_string.append(list.get(i).getKey()+",,,"+list.get(i).getValue().getValue()+"\n");
        }
            output_string.append("\n");
        }


        //文件输出
        File file1 = new File("E:\\右眼_老数据_all.csv");
        BufferedWriter bw = new BufferedWriter(new FileWriter(file1));
        bw.write(output_string.toString());
        bw.close();

    }
    public static double distance(Instance x,Instance y){
        double sum=0;
        for(int i=0;i<x.numAttributes();i++){
            sum+=(y.value(i)-x.value(i))*(y.value(i)-x.value(i));
        }
        return Math.sqrt(sum);
    }
    public static void sort( ArrayList<Pair<Instance,Pair<Instance,Double>>>  DistanceList){
       Pair temp;
        for(int i=0;i<DistanceList.size();i++){
          for(int j=i+1;j<DistanceList.size();j++){
              if(DistanceList.get(i).getValue().getValue()>DistanceList.get(j).getValue().getValue()){
                  temp=DistanceList.get(i);
                  DistanceList.set(i,DistanceList.get(j));
                  DistanceList.set(j,temp);
              }
          }
      }
    }
}
