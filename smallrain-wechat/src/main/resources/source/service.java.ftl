package ${package.Service};

import ${package.Entity}.${entity};

import com.smallrain.wechat.common.exception.SmallrainException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smallrain.wechat.common.model.QueryParam;
import java.util.List;

/**
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${table.serviceName} {
  
  public IPage<${entity}> getList(QueryParam<${entity}> param) throws SmallrainException;
  
  public ${entity} getOne(String id) throws SmallrainException;
  
  public ${entity} add(${entity} entity) throws SmallrainException;
  
  public ${entity} update(${entity} entity) throws SmallrainException;
  
  public boolean delete(String... ids) throws SmallrainException;
}
