package ${package.Service};

import ${package.Entity}.${entity};

import com.smallrain.wechat.common.exception.SmallrainException;
import java.util.List;

/**
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${table.serviceName} {
  
  public List<${entity}> getList() throws SmallrainException;
  
  public ${entity} getOne(String id) throws SmallrainException;
  
  public ${entity} add(${entity} entity) throws SmallrainException;
  
  public ${entity} update(${entity} entity) throws SmallrainException;
  
  public boolean delete(String... ids) throws SmallrainException;
}
