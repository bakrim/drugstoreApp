import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISession } from 'app/shared/model/session.model';
import { getEntities as getSessions } from 'app/entities/session/session.reducer';
import { IHistorique } from 'app/shared/model/historique.model';
import { getEntity, updateEntity, createEntity, reset } from './historique.reducer';

export const HistoriqueUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const sessions = useAppSelector(state => state.session.entities);
  const historiqueEntity = useAppSelector(state => state.historique.entity);
  const loading = useAppSelector(state => state.historique.loading);
  const updating = useAppSelector(state => state.historique.updating);
  const updateSuccess = useAppSelector(state => state.historique.updateSuccess);

  const handleClose = () => {
    navigate('/historique');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSessions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...historiqueEntity,
      ...values,
      session: sessions.find(it => it.id.toString() === values.session.toString()),
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
          date: displayDefaultDateTime(),
        }
      : {
          ...historiqueEntity,
          date: convertDateTimeFromServer(historiqueEntity.date),
          session: historiqueEntity?.session?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="drugStoreApp.historique.home.createOrEditLabel" data-cy="HistoriqueCreateUpdateHeading">
            <Translate contentKey="drugStoreApp.historique.home.createOrEditLabel">Create or edit a Historique</Translate>
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
                  id="historique-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('drugStoreApp.historique.action')}
                id="historique-action"
                name="action"
                data-cy="action"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.historique.date')}
                id="historique-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="historique-session"
                name="session"
                data-cy="session"
                label={translate('drugStoreApp.historique.session')}
                type="select"
              >
                <option value="" key="0" />
                {sessions
                  ? sessions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/historique" replace color="info">
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

export default HistoriqueUpdate;
