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
        Instances olditems=items;
        //属性归一化
        Normalize normalize = new Normalize();
        normalize.setInputFormat(items);
        items= Filter.useFilter(items, normalize);

        for(int i=18;i<42;i++){
            items.deleteAttributeAt(items.numAttributes()-1);
        }
        items.deleteAttributeAt(5);
        items.deleteAttributeAt(2);
        items.deleteAttributeAt(1);
        items.deleteAttributeAt(0);
        ArrayList<Pair<Instance,Pair<Instance,Double>>> list=new ArrayList<>();
        Instance temp=items.instance(0);
        System.out.println(items);
        for(int i=0;i<items.numInstances();i++){
            list.add(new Pair(olditems.instance(i),new Pair(items.instance(i),distance(temp,items.instance(i)))));
        }
        sort(list);
        for(int i=0;i<10;i++){
            System.out.println("uuid:"+list.get(i).getKey().value(0)+"    distance:"+list.get(i).getValue().getValue());
        }

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
