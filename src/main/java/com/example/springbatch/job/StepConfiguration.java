package com.example.springbatch.job;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Member;

@Configuration
@RequiredArgsConstructor
public class StepConfiguration {
    
    private final StepBuilderFactory stepBuilderFactory;
    
    
//    public Step taskStep(){
//        return  this.stepBuilderFactory.get("step")
//                .tasklet(myTasklet())
//                .build();
//    }
//    TaskletStep 직접 생성한 Tasklet 실행 
   
//   public Step taskStep() {
//       return this.stepBuilderFactory.get("step")
//               .<Member , Member>chunk(100)
//               .reader(reader())
//               .writer(writer())
//               .build();
//   }
//    TaskletStep ChunkOrientedTasklet을 실행 

//   public Step jobStep() {
//   return this.stepBuilderFactory.get("step")
//               .job(job())
//               .launcher(jobLauncher)
//               .parametersExtractor(jobParameterExtractor())
//               .build();
//   }
//    JobStep Step에서 Job을 실행
    
//    public Step flowStep() {
//        return this.stepBuilderFactory.get("step")
//                .flow(myFLow())
//                .build();
//    }
//    FlowStep Step에서 Flow을 실행
}
