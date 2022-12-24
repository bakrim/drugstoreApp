import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './document.reducer';

export const DocumentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const documentEntity = useAppSelector(state => state.document.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="documentDetailsHeading">
          <Translate contentKey="drugStoreApp.document.detail.title">Document</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{documentEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="drugStoreApp.document.name">Name</Translate>
            </span>
          </dt>
          <dd>{documentEntity.name}</dd>
          <dt>
            <span id="thumbnailName">
              <Translate contentKey="drugStoreApp.document.thumbnailName">Thumbnail Name</Translate>
            </span>
          </dt>
          <dd>{documentEntity.thumbnailName}</dd>
          <dt>
            <span id="mimeType">
              <Translate contentKey="drugStoreApp.document.mimeType">Mime Type</Translate>
            </span>
          </dt>
          <dd>{documentEntity.mimeType}</dd>
          <dt>
            <span id="thumbnailPath">
              <Translate contentKey="drugStoreApp.document.thumbnailPath">Thumbnail Path</Translate>
            </span>
          </dt>
          <dd>{documentEntity.thumbnailPath}</dd>
          <dt>
            <span id="downloadPath">
              <Translate contentKey="drugStoreApp.document.downloadPath">Download Path</Translate>
            </span>
          </dt>
          <dd>{documentEntity.downloadPath}</dd>
          <dt>
            <span id="thumbnailDownloadPath">
              <Translate contentKey="drugStoreApp.document.thumbnailDownloadPath">Thumbnail Download Path</Translate>
            </span>
          </dt>
          <dd>{documentEntity.thumbnailDownloadPath}</dd>
          <dt>
            <span id="uploadDate">
              <Translate contentKey="drugStoreApp.document.uploadDate">Upload Date</Translate>
            </span>
          </dt>
          <dd>
            {documentEntity.uploadDate ? <TextFormat value={documentEntity.uploadDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="path">
              <Translate contentKey="drugStoreApp.document.path">Path</Translate>
            </span>
          </dt>
          <dd>{documentEntity.path}</dd>
          <dt>
            <span id="documentSize">
              <Translate contentKey="drugStoreApp.document.documentSize">Document Size</Translate>
            </span>
          </dt>
          <dd>{documentEntity.documentSize}</dd>
        </dl>
        <Button tag={Link} to="/document" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/document/${documentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DocumentDetail;
