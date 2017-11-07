import javafx.util.Pair;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

import java.util.ArrayList;

/**
 * Created by 38147 on 2017/11/7.
 */
public class Smote {
    private Instances norm_items; //归一化之后的数据，用于计算距离
    private Instances items_14;//用作输出的数据，包含输出的14种属性
    private ArrayList<Node> Nodes=new ArrayList<>();
    public Smote(Instances Data,Instances Cal_data) throws Exception {
        items_14=Data;
        Normalize normalize = new Normalize();
        normalize.setInputFormat(Cal_data);
        norm_items=Filter.useFilter(Cal_data, normalize);
        for(int i=0;i<Data.numInstances();i++){
           Nodes.add(new Node(items_14.instance(i),norm_items.instance(i)));
        }
    }

    /**
     *
     * @param group：到扩充的组
     * @param k：K近邻
     * @param r：扩大倍数
     * @return
     */
    public Instances expansion(Instances group, int k,int r) {
        Instances newdata=new Instances(group);
       for(int i=0;i<group.numInstances();i++){
           Instance[] near=TopK(group.instance(i),k);
           for(int j=0;j<near.length;j++){
               newdata.add(GetNewInstance(group.instance(i),near[(int)Math.round(k*Math.random())]));
           }
       }
    }

    /**
     *
     * @param j  第j个节点
     * @param k  返回K个最近邻
     * @return
     */
    private Instance[] TopK(Instance instance,int k){
        ArrayList<Pair<Node,Double>> DistanceList=new ArrayList<>();
        for(int i=0;i<Nodes.size();i++){
            DistanceList.add(new Pair<Nodes., Double>());
        }
    }
    public void sort( ArrayList<Pair<Node,Double>>  DistanceList) {
        Pair temp;
        for (int i = 0; i < DistanceList.size(); i++) {
            for (int j = i + 1; j < DistanceList.size(); j++) {
                if (DistanceList.get(i).getValue().getValue() > DistanceList.get(j).getValue().getValue()) {
                    temp = DistanceList.get(i);
                    DistanceList.set(i, DistanceList.get(j));
                    DistanceList.set(j, temp);
                }
            }
        }
    }
    private double distance(Instance x,Instance y){
        double sum=0;
        for(int i=0;i<x.numAttributes();i++){
            sum+=(y.value(i)-x.value(i))*(y.value(i)-x.value(i));
        }
        return Math.sqrt(sum);
    }
    private Instance GetNewInstance(Instance instance, Instance neighbor) {  //计算新的数据    x_new=x_old+(x_near-xold)*rand
        Instance newinstance = new Instance(instance.numAttributes());  //创建一个长度为
        newinstance.setDataset(items);
        double rand = Math.random();
        double x_new;
        for (int i = 0; i < instance.numAttributes(); i++) {
            x_new = instance.value(i) + (neighbor.value(i) - instance.value(i)) * rand;
            if (i == 3 || i == 4 || i == 5) {  //四舍五入  age，sex，eye
                newinstance.setValue(i, Math.round(x_new));
            }
            newinstance.setValue(i, x_new);
        }
        return  newinstance;
    }
}
class Node{
    private Instance orgin;
    private Instance norm;
    public Node(Instance orgin,Instance norm){
        this.orgin=orgin;
        this.norm=norm;
    }
    public Instance getNorm() {
        return norm;
    }
    public Instance getOrgin(){
        return orgin;
    }
}