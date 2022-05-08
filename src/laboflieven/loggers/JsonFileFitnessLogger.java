package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.common.AccInstructionOpcode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonFileFitnessLogger implements FitnessLogger
{
    private class InstructionNode
    {
        InstructionNode(String name, Double error, List<InstructionNode> children) {
            this.name = name;
            this.error = error;
            this.children = children;
        }

        InstructionNode(String name, Double error) {
            this.name = name;
            this.error = error;
            this.children = new ArrayList<>();
        }

        String name;
        Double error;
        List<InstructionNode> children;
    }
    private final FileWriter writer;
    private final InstructionNode rootParent = new InstructionNode("",255.0, new ArrayList<>());

    public JsonFileFitnessLogger(File file) throws IOException {
        writer = new FileWriter(file);
    }

    @Override
    public void addFitness(List<InstructionMark> instructions, int nrInstruction, int nrRegisters, double error) {
        List<String> parentStream = instructions.stream()
                .map(i -> i.getInstructionOpcode().getName())
                .collect(Collectors.toList());
        InstructionNode currentNode = rootParent;
        for (int curChain = 0; curChain < parentStream.size(); curChain++)
        {
            String chain = parentStream.get(curChain);
            boolean foundChain = false;
            for (InstructionNode n : currentNode.children)
            {
                if (n.name.equals(chain)) {
                    foundChain = true;
                    currentNode = n;
                    break;
                }
            }

            if (foundChain && isLastPiece(parentStream, curChain) && error < currentNode.error) {
                currentNode.error = error;
            } else if (!foundChain && isLastPiece(parentStream, curChain)) {
                addChildWithErrorCode(error, currentNode, chain);
            } else if (!foundChain) {
                addEmptyChild(currentNode, chain);
            }
        }
    }

    private void addEmptyChild(InstructionNode currentNode, String chain) {
        currentNode.children.add(new InstructionNode(chain, 0.0));
    }

    private void addChildWithErrorCode(double error, InstructionNode currentNode, String chain) {
        currentNode.children.add(new InstructionNode(chain, error));
    }

    private boolean isLastPiece(List<String> parentStream, int curChain) {
        return curChain == parentStream.size() - 1;
    }

    public void finish() throws IOException {
        JSONObject obj = nodeToJson(rootParent, getList(rootParent.children));
        writer.write(obj.toJSONString());
        writer.close();
    }

    private JSONArray getList(List<InstructionNode> nodelist) {
        JSONArray list = new JSONArray();
        for (InstructionNode node: nodelist) {
            list.add(nodeToJson(node, getList(node.children)));
        }
        return list;
    }

    private JSONObject nodeToJson(InstructionNode node, JSONArray list)
    {
        JSONObject obj = new JSONObject();
        obj.put("name", node.name);
        obj.put("error", node.error);
        obj.put("children", list);
        return obj;
    }

}
