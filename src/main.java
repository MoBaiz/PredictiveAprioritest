import javafx.util.Pair;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;

/**
 * Created by 38147 on 2017/10/31.
 */
public class main {
    private static Instances allitems;
    private static Instances items;
    public static void main(String args[]) throws Exception {
        DB db=new DB("jdbc:mysql://localhost:3306/eye","root","zch123");
        int[] deletepoint={5,4,2,1,0};
        String sql="select * from nomogram";
        allitems=db.getData(sql);
        items=db.getData(sql);
        Instance newinst=new Instance(2);
        newinst.setDataset(items);
        newinst.setValue(0,1);
      //  newinst.setValue(1,2);
     //   newinst.setValue(2,"shanghai");

        System.out.println(newinst);
//
//        //删除LeadEye之后的属性
//        for(int i=18;i<42;i++){
//            items.deleteAttributeAt(items.numAttributes()-1);
//        }
//        for(int i=0;i<deletepoint.length;i++){
//            items.deleteAttributeAt(deletepoint[i]);
//        }
//
//        //属性归一化
//        Normalize normalize = new Normalize();
//        normalize.setInputFormat(items);
//        items= Filter.useFilter(items, normalize);
//
//        //
//        ArrayList<Pair<Instance,Pair<Instance,Double>>> list=new ArrayList<>();  //list 存储实例化前和实例化后数据的映射关系


//
//        File file1 = new File("E:\\左眼_新数据_14_去性别.csv");
//        BufferedWriter bw = new BufferedWriter(new FileWriter(file1));
//        bw.write(output_string.toString());
//        bw.close();

    }
//    private static HashMap<> Classify(int){
//
//    }
    //计算新的属性      smote 算法
    private static Instance GetNewInstance(Instance instance,Instance neighbor){  //计算新的数据
        Instance newinstance=new Instance(instance.numAttributes());  //创建一个长度为

        for(int i=0;i<instance.numAttributes();i++){
            newinstance.setValue();
        }
    //规范化眼别，性别
    return newinstance;
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
