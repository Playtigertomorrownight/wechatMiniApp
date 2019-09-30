package ${package.Controller};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.smallrain.wechat.common.Response;
import com.smallrain.wechat.common.exception.SmallrainException;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/${table.entityPath}")
public class ${table.controllerName} {

    @Autowired
    public ${table.serviceName} ${table.entityPath}Service;

    @GetMapping("")
    public Response list() throws SmallrainException {
      log.info("获取  ${entity} 列表");
      return Response.success(${table.entityPath}Service.getList());
    }
    
    @PostMapping("")
    public Response add(@RequestBody ${entity} entity) throws SmallrainException  {
      log.info("添加一条 ${entity} 记录");
      return Response.success(${table.entityPath}Service.add(entity));
    }
    
    @PutMapping("")
    public Response put(@RequestBody ${entity} entity) throws SmallrainException  {
      log.info("更新一条 ${entity} 记录");
      return Response.success(${table.entityPath}Service.update(entity));
    }
    
    @GetMapping("/{id}")
    public Response get(@PathVariable String id) throws SmallrainException  {
      log.info("根据 ID：{} 获取一条 ${entity} 记录",id);
      return Response.success(${table.entityPath}Service.getOne(id));
    }
    
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable String id) throws SmallrainException  {
      log.info("根据 ID：{} 删除一条 ${entity} 记录",id);
      return Response.success(${table.entityPath}Service.delete(id));
    }
}
