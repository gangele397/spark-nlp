package com.johnsnowlabs.nlp

import org.apache.spark.ml.util.{DefaultParamsReadable, Identifiable}

/**
  * Created by saif on 06/07/17.
  */

class TokenAssembler(override val uid: String) extends AnnotatorModel[TokenAssembler]{

  import com.johnsnowlabs.nlp.AnnotatorType._

  override val annotatorType: AnnotatorType = DOCUMENT

  override val requiredAnnotatorTypes: Array[String] = Array(TOKEN)

  def this() = this(Identifiable.randomUID("TOKEN_ASSEMBLER"))

  override protected def annotate(annotations: Seq[Annotation]): Seq[Annotation] = {
    annotations.groupBy(token => token.result)
      .map{case (_, sentenceAnnotations) =>
          Annotation(
            DOCUMENT,
            sentenceAnnotations.minBy(_.start).start,
            sentenceAnnotations.maxBy(_.end).end,
            sentenceAnnotations.map(_.result).mkString(" "),
            Map.empty[String, String]
          )
      }.toSeq
  }

}
object TokenAssembler extends DefaultParamsReadable[TokenAssembler]