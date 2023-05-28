package buslaev.service;

import buslaev.build.Autowired;
import buslaev.build.Component;
import buslaev.model.Model;
import buslaev.model.ModelSQLDAO;

@Component
public class Service {

    @Autowired
    private ModelSQLDAO modelDao;

    public Model getModel(int id) {
        return modelDao.getModel(id);
    }

    public void create(Model model) {
        modelDao.addNewModel(model);
    }

    public void update(Model model) {
        modelDao.updateModel(model);
    }

}
