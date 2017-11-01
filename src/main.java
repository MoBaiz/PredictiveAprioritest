import javafx.util.Pair;
import weka.core.Instance;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

import java.time.Instant;
import java.util.ArrayList;

/**
 * Created by 38147 on 2017/10/31.
 */
public class main {
    public static void main(String args[]) throws Exception {
        DB db=new DB("jdbc:mysql://localhost:3306/eye","root","zch123");
        Instances items=db.getData("select * from nomogram where eye='OS'");
        Instances allitems=items;
        //属性归一化
        Normalize normalize = new Normalize();
        normalize.setInputFormat(items);
        items= Filter.useFilter(items, normalize);
        //删除无用属性
        for(int i=18;i<42;i++){
            items.deleteAttributeAt(items.numAttributes()-1);
        }
        items.deleteAttributeAt(5);
        items.deleteAttributeAt(2);
        items.deleteAttributeAt(1);
        items.deleteAttributeAt(0);
        ArrayList<Pair<Instance,Pair<Instance,Double>>> list=new ArrayList<>();
        Instance temp=items.instance(0);
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
            output_string.append(list.get(0).getKey()+",,"+list.get(0).getValue().getValue()+"\n");
            output_string.append("Rank:\n");
        for(int i=1;i<11;i++){
            output_string.append(list.get(i).getKey()+",,"+list.get(i).getValue().getValue()+"\n");
        }
            output_string.append("\n");
        }
        System.out.println(output_string.toString());
    }
    public void write(Instances data){

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
