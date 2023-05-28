package buslaev.service;

import buslaev.build.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PathParamService {

    public Map<String, Integer> getParams(String path) {
        Map<String, Integer> paramsMap = new HashMap<>();
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '?') {
                path = path.substring(0, i + 1);
                break;
            }
        }
        int start = 0;
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '&' || i == path.length() - 1) {
                String[] params = path.substring(start, i + 1).split("=");
                paramsMap.put(params[0], Integer.parseInt(params[1]));
                start = i + 1;
            }
        }
        return paramsMap;
    }
}
