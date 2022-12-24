import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDossierPharmacie } from 'app/shared/model/dossier-pharmacie.model';
import { getEntities as getDossierPharmacies } from 'app/entities/dossier-pharmacie/dossier-pharmacie.reducer';
import { IDossierAutre } from 'app/shared/model/dossier-autre.model';
import { getEntities as getDossierAutres } from 'app/entities/dossier-autre/dossier-autre.reducer';
import { IDocument } from 'app/shared/model/document.model';
import { getEntity, updateEntity, createEntity, reset } from './document.reducer';

export const DocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dossierPharmacies = useAppSelector(state => state.dossierPharmacie.entities);
  const dossierAutres = useAppSelector(state => state.dossierAutre.entities);
  const documentEntity = useAppSelector(state => state.document.entity);
  const loading = useAppSelector(state => state.document.loading);
  const updating = useAppSelector(state => state.document.updating);
  const updateSuccess = useAppSelector(state => state.document.updateSuccess);

  const handleClose = () => {
    navigate('/document');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDossierPharmacies({}));
    dispatch(getDossierAutres({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.uploadDate = convertDateTimeToServer(values.uploadDate);

    const entity = {
      ...documentEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          uploadDate: displayDefaultDateTime(),
        }
      : {
          ...documentEntity,
          uploadDate: convertDateTimeFromServer(documentEntity.uploadDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="drugStoreApp.document.home.createOrEditLabel" data-cy="DocumentCreateUpdateHeading">
            <Translate contentKey="drugStoreApp.document.home.createOrEditLabel">Create or edit a Document</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="document-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('drugStoreApp.document.name')} id="document-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('drugStoreApp.document.thumbnailName')}
                id="document-thumbnailName"
                name="thumbnailName"
                data-cy="thumbnailName"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.document.mimeType')}
                id="document-mimeType"
                name="mimeType"
                data-cy="mimeType"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.document.thumbnailPath')}
                id="document-thumbnailPath"
                name="thumbnailPath"
                data-cy="thumbnailPath"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.document.downloadPath')}
                id="document-downloadPath"
                name="downloadPath"
                data-cy="downloadPath"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.document.thumbnailDownloadPath')}
                id="document-thumbnailDownloadPath"
                name="thumbnailDownloadPath"
                data-cy="thumbnailDownloadPath"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.document.uploadDate')}
                id="document-uploadDate"
                name="uploadDate"
                data-cy="uploadDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('drugStoreApp.document.path')} id="document-path" name="path" data-cy="path" type="text" />
              <ValidatedField
                label={translate('drugStoreApp.document.documentSize')}
                id="document-documentSize"
                name="documentSize"
                data-cy="documentSize"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/document" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default DocumentUpdate;
